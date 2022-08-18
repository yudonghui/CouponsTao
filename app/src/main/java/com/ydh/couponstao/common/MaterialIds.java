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
            case 1:
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
            case 2:
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
            case 3:
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
            case 4:
                list.add(new TitleEntity("27446", "综合"));
                list.add(new TitleEntity("27448", "女装"));
                list.add(new TitleEntity("27451", "食品"));
                list.add(new TitleEntity("27453", "美妆个护"));
                list.add(new TitleEntity("27798", "家居家装"));
                list.add(new TitleEntity("27454", "母婴"));
                break;
        }
        return list;
    }
    /*
    *综合	女装	食品	美妆个护	家居家装	母婴
27446	27448	27451	27453	27798	27454
    * */
}
