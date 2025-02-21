package com.jees.tool.utils;

import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 提供基础数据类型的相关操作
 * 
 * @author aiyoyoyo
 *
 */
@Log4j2
public class DataUtil {

	public static final String Charset_UTF8 = "UTF-8";

	/**
	 * int转byte[]
	 *
	 * @param _val 数字
	 * @return 字节数组
	 */
	public static byte[] int2bytes(int _val) {
		return new byte[]{(byte) ((_val >> 24) & 0xFF), (byte) ((_val >> 16) & 0xFF),
				(byte) ((_val >> 8) & 0xFF), (byte) (_val & 0xFF)};
	}

	/**
	 * byte[]转int
	 *
	 * @param _val 字节
	 * @return int数字
	 */
	public static int bytes2int(byte[] _val) {
		return _val[3] & 0xFF | (_val[2] & 0xFF) << 8 | (_val[1] & 0xFF) << 16 | (_val[0] & 0xFF) << 24;
	}

	/**
	 * String转byte[]
	 *
	 * @param _val 字符串
	 * @param _chs 字符解码类型，建议UTF-8，参考：DataUtil.Charset_UTF8
	 * @return 字节数组
	 * @throws UnsupportedEncodingException 不支持编码异常
	 */
	public static byte[] str2bytes(String _val, String _chs) throws UnsupportedEncodingException {
		return _val.getBytes(_chs);
	}

	/**
	 * bytes转String
	 *
	 * @param _val 字节数组
	 * @param _chs 字符解码类型，建议UTF-8，参考：DataUtil.Charset_UTF8
	 * @return 字符串
	 * @throws UnsupportedEncodingException 不支持编码异常
	 */
	public static String bytes2str(byte[] _val, String _chs) throws UnsupportedEncodingException {
		return new String(_val, _chs);
	}

	/**
	 * 将int类型的数字进行高低位互转
	 *
	 * @param _val 数字
	 * @return 低位数字
	 */
	public static int warpHL(int _val) {
		return ByteBuffer.wrap(int2bytes(_val)).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	/**
	 * 将long类型的数字进行高低位互转
	 *
	 * @param _val 数字
	 * @return 低位数字
	 */
	public static long warpHL(long _val) {
		return ByteBuffer.wrap(long2bytes(_val)).order(ByteOrder.LITTLE_ENDIAN).getLong();
	}

	/**
	 * 将int类型的数字进行高低位互转
	 * @param _val 数字
	 * @return 高位数字
	 */
	public static int warpLH(int _val){
		return ByteBuffer.wrap(int2bytes(_val)).order(ByteOrder.BIG_ENDIAN).getInt();
	}

	/**
	 * 将int类型的数字进行高低位互转
	 * @param _val 数字
	 * @return 高位数字
	 */
	public static long warpLH(long _val){
		return ByteBuffer.wrap(long2bytes(_val)).order(ByteOrder.BIG_ENDIAN).getLong();
	}
	/**
	 * 截取bytes
	 *
	 * @param _data 字节数组
	 * @param _start 起始位
	 * @param _end 结束位
	 * @return 截取的字节数组
	 */
	public static byte[] subBytes(byte[] _data, int _start, int _end) {
		byte[] ret = new byte[_end - _start];
		for (int i = 0; (_start + i) < _end; i++) {
			ret[i] = _data[_start + i];
		}
		return ret;
	}

	/**
	 * long转bytes
	 *
	 * @param _val 数字
	 * @return 字节数组
	 */
	public static byte[] long2bytes(long _val) {
		byte[] ret = new byte[8];
		for (int i = 0; i < 8; ++i) {
			int offset = 64 - (i + 1) * 8;
			ret[i] = (byte) ((_val >> offset) & 0xff);
		}
		return ret;
	}

	/**
	 * bytes转long
	 *
	 * @param _val 字节数组
	 * @return 数字
	 */
	public static long bytes2long(byte[] _val) {
		long num = 0;
		for (int i = 0; i < 8; ++i) {
			num <<= 8;
			num |= (_val[i] & 0xff);
		}
		return num;
	}

	public static String num10_2_2( int _val ){
		return Integer.toBinaryString( _val );
	}
	public static String num10_2_8( int _val ){
		return Integer.toOctalString( _val );
	}
	public static String num10_2_16( int _val ){
		return Integer.toHexString( _val );
	}
	public static String num2_2_10( String _val ){
		return "" + Integer.parseInt( _val, 2 );
	}
	public static String num8_2_10( String _val ){
		return "" + Integer.parseInt( _val, 8 );
	}
	public static String num16_2_10( String _val ){
		return "" + Integer.parseInt( _val, 16 );
	}

	public static String num16_2_2( String _val ){
		return num10_2_2( Integer.parseInt( num16_2_10( _val ) ) );
	}

	public static String bytes_2_hex(byte[] _val) {
		StringBuffer sb = new StringBuffer(_val.length);
		String sTemp;
		for (int i = 0; i < _val.length; i++) {
			sTemp = Integer.toHexString(0xFF & _val[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}


	public static String fixbit( String _val, int _size ){
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < _size - _val.length(); i++) {
			sb.append( "0" );
		}
		sb.append( _val );
		return sb.toString();
	}

	public static String hex_2_str(String _val) {
		String str = "0123456789ABCDEF";
		char[] hexs = _val.toCharArray();
		byte[] bytes = new byte[_val.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
}
