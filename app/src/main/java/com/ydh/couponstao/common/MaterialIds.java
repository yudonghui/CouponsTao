package com.ydh.couponstao.common;

import com.ydh.couponstao.entitys.TitleEntity;

import java.util.ArrayList;


/**
 * Created by ydh on 2022/8/16
 */
public class MaterialIds {
    public static ArrayList<TitleEntity> getMaterial(int type) {
        ArrayList<TitleEntity> list = new ArrayList<>();
        switch (type) {
            case 1://高佣榜（）
                list.add(new TitleEntity("13366", "综合"));
                list.add(new TitleEntity("13367", "女装"));
                list.add(new TitleEntity("13372", "男装"));
                list.add(new TitleEntity("13368", "家居家装"));
                list.add(new TitleEntity("13369", "数码家电"));
                list.add(new TitleEntity("13370", "鞋包配饰"));
                list.add(new TitleEntity("13371", "美妆个护"));
                list.add(new TitleEntity("13373", "内衣"));
                list.add(new TitleEntity("13374", "母婴"));
                list.add(new TitleEntity("13375", "食品"));
                list.add(new TitleEntity("13376", "运动户外"));
                break;
            case 2://好券直播
                list.add(new TitleEntity("3756", "综合"));
                list.add(new TitleEntity("3767", "女装"));
                list.add(new TitleEntity("3764", "男装"));
                list.add(new TitleEntity("3758", "家居家装"));
                list.add(new TitleEntity("3759", "数码家电"));
                list.add(new TitleEntity("3762", "鞋包配饰"));
                list.add(new TitleEntity("3763", "美妆个护"));
                list.add(new TitleEntity("3765", "内衣"));
                list.add(new TitleEntity("3760", "母婴"));
                list.add(new TitleEntity("3761", "食品"));
                list.add(new TitleEntity("3766", "运动户外"));
                break;
            case 3://品牌券
                list.add(new TitleEntity("3786", "综合"));
                list.add(new TitleEntity("3788", "女装"));
                list.add(new TitleEntity("3790", "男装"));
                list.add(new TitleEntity("3792", "家居家装"));
                list.add(new TitleEntity("3793", "数码家电"));
                list.add(new TitleEntity("3796", "鞋包配饰"));
                list.add(new TitleEntity("3794", "美妆个护"));
                list.add(new TitleEntity("3787", "内衣"));
                list.add(new TitleEntity("3789", "母婴"));
                list.add(new TitleEntity("3791", "食品"));
                list.add(new TitleEntity("3795", "运动户外"));
                break;
            case 4://大额券
                list.add(new TitleEntity("27446", "综合"));
                list.add(new TitleEntity("27448", "女装"));
                list.add(new TitleEntity("27451", "食品"));
                list.add(new TitleEntity("27453", "美妆个护"));
                list.add(new TitleEntity("27798", "家居家装"));
                list.add(new TitleEntity("27454", "母婴"));
                break;
            case 5://特惠
                list.add(new TitleEntity("4094", "特惠"));
                break;
            case 6://潮流范
                list.add(new TitleEntity("4093", "潮流范"));
                break;
            case 7://母婴主题
                list.add(new TitleEntity("4040", "备孕"));
                list.add(new TitleEntity("4041", "0至6个月"));
                list.add(new TitleEntity("4042", "7至12个月"));
                list.add(new TitleEntity("4043", "1至3岁"));
                list.add(new TitleEntity("4044", "4至6岁"));
                list.add(new TitleEntity("4045", "7至12岁"));
                break;
            case 8://实时热销榜
                list.add(new TitleEntity("28026", "综合"));
                list.add(new TitleEntity("28027", "大快消"));
                list.add(new TitleEntity("28028", "电器美家"));
                list.add(new TitleEntity("28029", "大服饰"));
                break;
            case 9://
                list.add(new TitleEntity("32366", "聚划算"));
                list.add(new TitleEntity("31362", "天天特卖相关"));
                list.add(new TitleEntity("27160", "天猫超市"));
                list.add(new TitleEntity("34616", "淘抢购"));
                list.add(new TitleEntity("4092", "有好货"));
                break;
        }
        return list;
    }

    /**
     * 频道ID:1-好券商品,2-精选卖场,10-9.9包邮,15-京东配送,22-实时热销榜,23-为你推荐,24-数码家电,25-超市,26-母婴玩具,27-家具日用,
     * 28-美妆穿搭,30-图书文具,31-今日必推,32-京东好物,33-京东秒杀,34-拼购商品,40-高收益榜,41-自营热卖榜,108-秒杀进行中,
     * 109-新品首发,110-自营,112-京东爆品,125-首购商品,129-高佣榜单,130-视频商品,153-历史最低价商品榜,210-极速版商品,238-新人价商品,
     * 247-京喜9.9,249-京喜秒杀,315-秒杀未开始,340-时尚趋势品,341-3C新品,342-智能新品,343-3C长尾商品,345-时尚新品,346-时尚爆品,426-京喜自营,1001-选品库,515-订单接龙商品
     */
    public static ArrayList<TitleEntity> getMaterial() {
        ArrayList<TitleEntity> list = new ArrayList<>();
        list.add(new TitleEntity("1", "好券商品"));
        list.add(new TitleEntity("2", "精选卖场"));
        list.add(new TitleEntity("10", "9.9包邮"));
        list.add(new TitleEntity("15", "京东配送"));
        list.add(new TitleEntity("22", "实时热销榜"));
        list.add(new TitleEntity("23", "为你推荐"));
        list.add(new TitleEntity("24", "数码家电"));
        list.add(new TitleEntity("25", "超市"));
        list.add(new TitleEntity("26", "母婴玩具"));
        list.add(new TitleEntity("27", "家具日用"));
        list.add(new TitleEntity("28", "美妆穿搭"));
        list.add(new TitleEntity("30", "图书文具"));
        list.add(new TitleEntity("31", "今日必推"));
        list.add(new TitleEntity("32", "京东好物"));
        list.add(new TitleEntity("33", "京东秒杀"));
        list.add(new TitleEntity("34", "拼购商品"));
        list.add(new TitleEntity("40", "高收益榜"));
        list.add(new TitleEntity("41", "自营热卖榜"));
        list.add(new TitleEntity("108", "秒杀进行中"));
        list.add(new TitleEntity("109", "新品首发"));
        list.add(new TitleEntity("110", "自营"));
        list.add(new TitleEntity("112", "京东爆品"));
        list.add(new TitleEntity("125", "首购商品"));
        list.add(new TitleEntity("129", "高佣榜单"));
        list.add(new TitleEntity("130", "视频商品"));
        list.add(new TitleEntity("153", "历史最低价商品榜"));
        list.add(new TitleEntity("345", "时尚新品"));
        list.add(new TitleEntity("346", "时尚爆品"));
        return list;
    }
}
