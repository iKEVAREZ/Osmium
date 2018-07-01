package com.kmecpp.osmium.api.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.kmecpp.jlib.function.StringFormatter;

public class ChatUtil {

	public static final char COLOR_CHAR = '\u00A7';

	/**
	 * Strips color codes from the given String
	 * 
	 * @param str
	 *            the String to strip
	 * @return the stripped version of the String
	 */
	public static String stripColorCodes(String str) {
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', str));
	}

	public static String reverseColor(String str) {
		return str.replace(COLOR_CHAR, '&');
	}

	/**
	 * Colors the String using the Essentials color code: '&'. Equivalent to<br>
	 * <br>
	 * <code>
	 * ChatColor.translateAlternateColorCodes('&', str);
	 * </code>
	 * 
	 * @param str
	 *            the String to color
	 * @return the colored version of the String
	 */
	public static String getColored(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	/**
	 * Strips the given message of all color. Equivalent to <br>
	 * <br>
	 * <code>ChatColor.stripColor(String)</code>
	 * 
	 * @param str
	 *            input String to strip of color
	 * @return a copy of the input String, without coloring
	 */
	public static String stripColor(String str) {
		return ChatColor.stripColor(str);
	}

	/**
	 * Strips Essentials formatting and magic codes from the given String
	 * 
	 * @param str
	 *            the String to strip
	 * @return the stripped version of the String
	 */
	public static String stripFormattingCodes(String str) {
		return str.replaceAll("(?<!&)&([k-orA-FK-OR])", "");
	}

	/**
	 * Strips all Essentials formatting and color codes from the given String
	 * 
	 * @param str
	 *            the String to strip
	 * @return the stripped version of the String
	 */
	public static String stripChatCodes(String str) {
		return stripColorCodes(stripFormattingCodes(str));
	}

	public static void sendList(CommandSender sender, String title, List<?> list) {
		sendList(sender, title, "", list);
	}

	public static void sendList(CommandSender sender, String title, String itemPrefix, List<?> list) {
		sendList(sender, title, CS.X6AB, itemPrefix, list);
	}

	public static void sendNumberedList(CommandSender sender, String title, CS colors, List<?> list) {
		sendTitle(sender, colors, title);
		for (int i = 0; i < list.size(); i++) {
			sender.sendMessage(colors.getTertiary() + " " + (i + 1) + ") " + colors.getSecondary() + list.get(i));
		}
	}

	public static <T> void sendList(CommandSender sender, String title, final CS colors, final String itemPrefix, List<T> list) {
		sendList(sender, title, list, colors, new StringFormatter<T>() {

			@Override
			public String format(T obj) {
				return colors.getTertiary() + " " + itemPrefix + " " + colors.getSecondary() + String.valueOf(obj);
			}

		});
	}

	public static <T> void sendList(CommandSender out, String title, CS colors, T[] arr, StringFormatter<T> message) {
		sendList(out, title, Arrays.asList(arr), colors, message);
	}

	public static <T> void sendList(CommandSender out, String title, Iterable<T> list, CS colors, StringFormatter<T> formatter) {
		sendTitle(out, colors, title);
		for (T obj : list) {
			out.sendMessage(colors.getTertiary() + formatter.format(obj));
		}
	}

	public static void sendTitle(CommandSender out, String title) {
		sendTitle(out, CS.XEA, title);
	}

	public static void sendItem(CommandSender out, String key, Object value) {
		out.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + key + ": " + ChatColor.GREEN + ChatColor.BOLD + String.valueOf(value));

	}

	public static void sendTitle(CommandSender out, CS colors, String title) {
		out.sendMessage("");
		out.sendMessage(colors.getPrimary() + ChatColor.BOLD.toString() + title);
		out.sendMessage(colors.getSecondary() + ChatColor.BOLD.toString() + ChatColor.STRIKETHROUGH + "----------------------------------------");
		out.sendMessage("");
	}

}