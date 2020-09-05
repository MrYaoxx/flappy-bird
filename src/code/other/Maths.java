package code.other;

/**
 * ��ѧ������
 * @author Creeper
 *
 */

public class Maths {
	/**
	 * �������(long)
	 * @param num
	 * @return
	 */
	 public static int[] resolution(long num) {
		 //����ֵ
		 int[] res = new int[("" + num).length()];
		 int pos = 0;
		 
		 //���
		 while(num != 0) {
			 res[pos++] = (int) (num % 10);
			 num /= 10;
		 }
		 
		 //��ת
		 for(int i = 0 ;i < res.length/2 ;i++) {
			 int tmp = res[i];
			 res[i] = res[res.length - i - 1];
			 res[res.length - i - 1] = tmp;
		 }

		 //����
		 return res;
	}
	 

		/**
		 * �������(int)
		 * @param num
		 * @return
		 */
	 public static int[] resolution(int num) {
		 //����ֵ
		 int[] res = new int[("" + num).length()];
		 int pos = 0;
		 
		 //���
		 while(num != 0) {
			 res[pos++] = (int) (num % 10);
			 num /= 10;
		 }
		 
		 //��ת
		 for(int i = 0 ;i < res.length/2 ;i++) {
			 int tmp = res[i];
			 res[i] = res[res.length - i - 1];
			 res[res.length - i - 1] = tmp;
		 }

		 //����
		 return res;
	}
}
