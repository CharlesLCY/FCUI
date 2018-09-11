package com.lcy.demo;

import android.databinding.ObservableField;

/**
 * @author FanCoder.LCY
 * @date 2018/9/10 14:19
 * @email 15708478830@163.com
 * @desc
 **/
public class ObItem {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<Integer> age = new ObservableField<>();

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<Integer> getAge() {
        return age;
    }

    public void setAge(ObservableField<Integer> age) {
        this.age = age;
    }

//    public String name;
//    public int age;
}
