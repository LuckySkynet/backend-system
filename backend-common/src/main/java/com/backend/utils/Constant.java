package com.backend.utils;

/**
 * 常量
 *
 * @author Skynet
 * @date 2017年04月22日 09:42
 */
public class Constant {

    /**
     * 超级管理员ID
     *
     * @author: Skynet
     * @date: 2017/4/22 10:00
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     *
     * @author: Skynet
     * @date: 2017/4/22 9:52
     */
    public enum MenuType{
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

}
