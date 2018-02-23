package com.lq.okex.utils;

import java.text.DecimalFormat;

public class DoubleFormat {
//	 double f = 111231.4585;    
//     public void m1() {    
//         BigDecimal bg = new BigDecimal(f);    
//         double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    
//         System.out.println(f1);    
//     }    
     /**  
      * DecimalFormat转换最简便  
      */    
     public static double d4(double num) {    
         //#.00 表示两位小数  
         DecimalFormat df = new DecimalFormat("#0.0000");    
         return Double.parseDouble(df.format(num));    
     }    
//       
//     /**  
//      * String.format打印最简便  
//      */    
//     public void m3() {    
//         //%.2f  %.表示 小数点前任意位数   2 表示两位小数 格式后的结果为f 表示浮点型  
//         System.out.println(String.format("%.2f", f));    
//     }    
//       
//     public void m4() {    
//          
//         NumberFormat nf = NumberFormat.getNumberInstance();    
//         //digits 显示的数字位数 为格式化对象设定小数点后的显示的最多位,显示的最后位是舍入的  
//         nf.setMaximumFractionDigits(2);    
//         System.out.println(nf.format(f));    
//     }   
//       
//     public static void main(String[] args) {    
//         DoubleFormat f = new DoubleFormat();    
//         f.m1();    
//         f.m2();    
//         f.m3();    
//         f.m4();    
//     }    
//       
       
     //还有一种直接向上取整数    
   //java:Java的取整函数  
  
   //Math.floor()、Math.ceil()、BigDecimal都是Java中的取整函数，但返回值却不一样    
                
    /*        Math.floor()  通过该函数计算后的返回值是舍去小数点后的数值   
            如：Math.floor(3.2)返回3   
            Math.floor(3.9)返回3   
            Math.floor(3.0)返回3   
               
            Math.ceil()   
            ceil函数只要小数点非0，将返回整数部分+1   
            如：Math.ceil(3.2)返回4   
            Math.ceil(3.9)返回4   
            Math.ceil(3.0)返回3*/  
}
