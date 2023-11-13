package cn.nansker.common.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author Nansker
 * @date 2023/9/18 19:16
 * @description 密码加密工具类
 */
public class PasswordUtils {

	/**
	 * 随机的字符和数字
	 */
	public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 盐值长度
	 */
	private static final int SALT_LENGTH = 6;

	/**
	 * 默认加密盐值
	 */
	public static final String DEFAULT_SALT = "nansker2023";

	/**
	 * @return java.lang.String
	 * @author Nansker
	 * @date 2023/9/18 20:15
	 * @description 生成盐值
	 */
	public static String generateSalt() {
		return getString(SALT_LENGTH);
	}

	/**
	 * @param password 原始密码
	 * @param salt     加密盐
	 * @return java.lang.String
	 * @author Nansker
	 * @date 2023/9/18 20:14
	 * @description 将密码进行MD5加盐加密
	 */
	public static String hashPassword(String salt, String password) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, SALT_LENGTH).substring(1));
			}
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hash;
	}

	/**
	 * @param password 原始密码
	 * @return java.lang.String
	 * @author Nansker
	 * @date 2023/9/18 20:14
	 * @description 将密码进行MD5加盐加密
	 */
	public static String hashPassword(String password) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(DEFAULT_SALT.getBytes());
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, DEFAULT_SALT.length()).substring(1));
			}
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hash;
	}

	/**
	 * @param salt           盐值
	 * @param password       原始密码
	 * @param hashedPassword 加密后的密码
	 * @return boolean
	 * @author Nansker
	 * @date 2023/9/18 20:15
	 * @description 比较密码是否相同
	 */
	public static boolean verifyPassword(String salt, String password, String hashedPassword) {
		String newHash = hashPassword(password, salt);
		return newHash.equals(hashedPassword);
	}

	/**
	 * @param password       原始密码
	 * @param hashedPassword 加密后的密码
	 * @return boolean
	 * @author Nansker
	 * @date 2023/9/18 20:15
	 * @description 比较密码是否相同
	 */
	public static boolean verifyPassword(String password, String hashedPassword) {
		String newHash = hashPassword(password);
		return newHash.equals(hashedPassword);
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 *
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String getString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
		}
		return sb.toString();
	}

}
