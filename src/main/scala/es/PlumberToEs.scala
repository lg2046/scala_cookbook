//package demo
//
//import com.sksamuel.elastic4s.ElasticDsl._
//import com.sksamuel.elastic4s.indexes.IndexDefinition
//import org.apache.spark.sql.SparkSession
//import org.joda.time.DateTime
//import org.json4s.{DefaultFormats, _}
//import org.json4s.native.JsonMethods.parse
//import rpc_client.EsClient
//import utils.ExtendOps.DateTimeOps._
//
//import scala.collection.mutable.ArrayBuffer
//
//object PlumberToEs {
//  val BULK_INSERT_COUNT = 200
//
//  implicit val jsonFormats: DefaultFormats.type = DefaultFormats
//
//  def main(args: Array[String]): Unit = {
//    val spark: SparkSession = SparkSession.builder()
//      .appName("PlumberRedSkuToES")
//      .enableHiveSupport()
//      .getOrCreate()
//
//    import spark.implicits._
//    import spark.sql
//
//    val namedArgs = args.map(x => x.split("--")).map(y => (y(0), y(1))).toMap
//    val date = namedArgs.getOrElse("dt", DateTime.now().minusDays(1).toDT)
//
//    /** 1: 先清空整个商品ES */
//    EsClient.client.execute {
//      deleteByQuery("fashion", "product", matchAllQuery())
//    }
//
//    // wait es flush db  20s
//    Thread.sleep(20000)
//
//    /** 2: 推送商品数据 */
//    val create_time = DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss")
//    val plumberData = sql(
//      s"""| SELECT
//          |     item_sku_id                     as skuId,
//          |     spu_id                          as spuId,
//          |     item_id                         as itemId,
//          |			regexp_replace(sku_name,'"',"'") as skuName,
//          |     if(ext_money_off_acts is null or size(ext_money_off_acts)=0, null, ext_money_off_acts) as promotionMJ,
//          |     if(ext_money_off_acts is null or size(ext_money_off_acts)=0, 0, 1) as mjFlag,
//          |     if(discountset is null or size(discountset)=0, null, discountset) as promotionStraightDown,
//          |     if(discountset is null or size(discountset)=0, 0, 1) as sdFlag,
//          |     if(ext_mn_acts is null or size(ext_mn_acts)=0, null, ext_mn_acts) as promotionMYNJ,
//          |     if(ext_mn_acts is null or size(ext_mn_acts)=0, 0, 1) as mynjFlag,
//          |			item_first_cate_cd              as firstCate,
//          |			item_first_cate_name            as firstCateName,
//          |			item_second_cate_cd             as secondCate,
//          |			item_second_cate_name           as secondCateName,
//          |			item_third_cate_cd              as thirdCate,
//          |			item_third_cate_name            as thirdCateName,
//          |			brand_code                      as brandId,
//          |			regexp_replace(brand_name,'"', "'") as brandName,
//          |     sale_mode                       as manageMode,
//          |			jd_prc                          as jdPrice,
//          |			case when otc_tm is null or otc_tm='' then null else regexp_replace(substring(otc_tm,0,19), ' ', 'T') end as onShelfDate,
//          |     otc_date_to_cur_days            as otcTime2curDays,
//          |			on_shelf_state                  as onShelfState,
//          |			ext_cate_info                   as extAttr,
//          |			vender_id                       as venderId,
//          |			vender_name                     as venderName,
//          |     shop_id                         as shopId,
//          |			regexp_replace(shop_name,'"', "'") as shopName,
//          |     if(sale_qtty_90 is null, null,
//          |     concat(if(sale_qtty_3 is null, '[', concat('[{\"timeslot\":3,\"saleqtty\":', sale_qtty_3, '},')),
//          |     if(sale_qtty_30 is null, '', concat('{\"timeslot\":30,\"saleqtty\":', sale_qtty_30, '},')),
//          |     '{\"timeslot\":90,\"saleqtty\":', sale_qtty_90,'}]')) as salesVolume,
//          |     if (lowest_price_90 is null, null,
//          |     concat(if(lowest_price_3 is null, '[', concat('[{\"timeslot\":3,\"minprice\":', lowest_price_3, '},')),
//          |     if(lowest_price_30 is null, '', concat('{\"timeslot\":30,\"minprice\":', lowest_price_30,'},')),
//          |     '{\"timeslot\":90,\"minprice\":', lowest_price_90,'}]')) as minimumPrice,
//          |     if (gmv_90 is null, null,
//          |     concat(if(gmv_3 is null, '[', concat('[{\"timeslot\":3,\"value\":', gmv_3, '},')),
//          |     if(gmv_30 is null, '', concat('{\"timeslot\":30,\"value\":', gmv_30,'},')),
//          |     '{\"timeslot\":90,\"value\":', gmv_90,'}]')) as gmv,
//          |     if (ord_trans_rate_90 is null, null,
//          |     concat(if(ord_trans_rate_3 is null, '[', concat('[{\"timeslot\":3,\"value\":', ord_trans_rate_3, '},')),
//          |     if(ord_trans_rate_30 is null, '', concat('{\"timeslot\":30,\"value\":', ord_trans_rate_30,'},')),
//          |     '{\"timeslot\":90,\"value\":', ord_trans_rate_90,'}]')) as ordTransRate,
//          |     if (pv_90 is null, null,
//          |     concat(if(pv_3 is null, '[', concat('[{\"timeslot\":3,\"value\":', pv_3, '},')),
//          |     if(pv_30 is null, '', concat('{\"timeslot\":30,\"value\":', pv_30,'},')),
//          |     '{\"timeslot\":90,\"value\":', pv_90,'}]')) as pv,
//          |     if (good_comment_rate_90 is null, null,
//          |     concat(if(good_comment_rate_3 is null, '[', concat('[{\"timeslot\":3,\"value\":', good_comment_rate_3, '},')),
//          |     if(good_comment_rate_30 is null, '', concat('{\"timeslot\":30,\"value\":', good_comment_rate_30,'},')),
//          |     '{\"timeslot\":90,\"value\":', good_comment_rate_90,'}]')) as goodCommentNum,
//          |     '$create_time'                  as create_time,
//          |     dept_id_1                       as firstDept,
//          |     dept_id_2                       as secondDept,
//          |     dept_id_3                       as thirdDept,
//          |     plus_flag                       as plusFlag,
//          |     case when plus_effect_time is null or plus_effect_time='' then null else regexp_replace(substring(plus_effect_time,0,19), ' ', 'T') end as plusEffectTime,
//          |     case when plus_end_time is null or plus_end_time='' then null else regexp_replace(substring(plus_end_time,0,19), ' ', 'T') end as plusEndTime,
//          |     pool_id                         as poolId,
//          |     sku_bi_score                    as skuBIScore,
//          |     sku_bi_score_std                as skuBIScoreStd,
//          |     cate3_lvl_score                 as cate3LevelScore,
//          |			cate3_lvl_score_std             as cate3LevelScoreStd,
//          |     sale_mode_score                 as saleModeScore,
//          |     third_cate_rand                 as thirdCateRank,
//          |     fourth_cate_rand                as fourthCateRank,
//          |     attr_group                      as attrGroup,
//          |			price_span                      as priceSpan,
//          |     comment_count                   as commentCount,
//          |			item_cate4                      as fourthCate,
//          |     model_flag                      as modelFlag,
//          |     attr_1                          as attr1,
//          |     attr_2                          as attr2,
//          |     attr_3                          as attr3,
//          |     attr_4                          as attr4,
//          |     sex_male                        as sexMale,
//          |     sex_female                      as sexFemale,
//          |     sex3_null                       as sex3Null,
//          |     age_15                          as age15,
//          |     age_25                          as age25,
//          |     age_35                          as age35,
//          |     age_45                          as age45,
//          |     age_55                          as age55,
//          |     age_56                          as age56,
//          |     age_null                        as ageNull,
//          |     purchase_lev_1                  as purchaseLev1,
//          |     purchase_lev_2                  as purchaseLev2,
//          |     purchase_lev_3                  as purchaseLev3,
//          |     purchase_lev_4                  as purchaseLev4,
//          |     purchase_lev_5                  as purchaseLev5,
//          |     purchase_lev_6                  as purchaseLev6,
//          |     new_flag                        as newFlag,
//          |     img_url                         as imgUrl,
//          |     new_product_flag                as newProductFlag,
//          |     find_goods                      as findGoods,
//          |     if(find_goods is not null, 0, null) as contentFlag,
//          |
//          |     coalesce(sale_qtty_3, 0) saleqtty3,
//          |     coalesce(sale_qtty_30, 0) saleqtty30,
//          |     coalesce(sale_qtty_90, 0) saleqtty90,
//          |     concat(item_third_cate_cd, '_', brand_code) thirdCateAndBrandCode,
//          |     row_number() over(partition by item_third_cate_cd, brand_code order by sku_bi_score_std desc) skuBiScoreRank
//          |  FROM
//          |     app.app_fashion_dw_item_sku_sum_da
//          |  WHERE dt='$date'
//          |
//       """.stripMargin)
//      .toJSON
//      .map(_.replace("\\", "")
//        .replaceAll("(:)\"(\\[\\{)([^\\]]+?)(\\}\\])\"(,|\\})", "$1$2$3$4$5")
//        .replaceAll("(,|\\[)\"(\\{)([\\s\\S}]+?)(\\})\"", "$1$2$3$4"))
//      .repartition(100)
//      .cache()
//
//    println(s"""Plumber sku to es data record count: ${plumberData.count()}""")
//
//    /** json字符串解析测试 */
//    val testParseJson = plumberData.map { str =>
//      parse(str)
//      true
//    }
//    println(s"json passed count: ${testParseJson.count}")
//
//
//    /** 批量插入 */
//    plumberData.foreachPartition { iterator =>
//      val opsBuffer = collection.mutable.ArrayBuffer[IndexDefinition]()
//
//      iterator.foreach { json_str =>
//        val doc_id = (parse(json_str) \ "skuId").extract[String]
//        opsBuffer.append(indexInto("fashion/product") id doc_id source json_str)
//
//        //达到批量 或者 分区操作结束前进行插入操作
//        if (opsBuffer.length == BULK_INSERT_COUNT) {
//          flushToES(opsBuffer)
//        }
//      }
//
//      if (opsBuffer.nonEmpty) {
//        flushToES(opsBuffer)
//      }
//
//    }
//
//    // wait es flush db  20s
//    Thread.sleep(20000)
//  }
//
//  def flushToES(opsBuffer: ArrayBuffer[IndexDefinition]): Unit = {
//    EsClient.client.execute {
//      bulk(opsBuffer: _*)
//    }.await
//    opsBuffer.clear()
//  }
//}