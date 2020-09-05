package code.other;

/**
 * 数学运算类
 * @author Creeper
 *
 */

public class Maths {
	/**
	 * 拆分数字(long)
	 * @param num
	 * @return
	 */
	 public static int[] resolution(long num) {
		 //返回值
		 int[] res = new int[("" + num).length()];
		 int pos = 0;
		 
		 //拆分
		 while(num != 0) {
			 res[pos++] = (int) (num % 10);
			 num /= 10;
		 }
		 
		 //反转
		 for(int i = 0 ;i < res.length/2 ;i++) {
			 int tmp = res[i];
			 res[i] = res[res.length - i - 1];
			 res[res.length - i - 1] = tmp;
		 }

		 //返回
		 return res;
	}
	 

		/**
		 * 拆分数字(int)
		 * @param num
		 * @return
		 */
	 public static int[] resolution(int num) {
		 //返回值
		 int[] res = new int[("" + num).length()];
		 int pos = 0;
		 
		 //拆分
		 while(num != 0) {
			 res[pos++] = (int) (num % 10);
			 num /= 10;
		 }
		 
		 //反转
		 for(int i = 0 ;i < res.length/2 ;i++) {
			 int tmp = res[i];
			 res[i] = res[res.length - i - 1];
			 res[res.length - i - 1] = tmp;
		 }

		 //返回
		 return res;
	}
}
