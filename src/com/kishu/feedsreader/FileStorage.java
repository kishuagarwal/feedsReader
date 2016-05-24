package com.kishu.feedsreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;

import org.json.JSONArray;

import android.content.Context;

public class FileStorage {

	private static String filename = "settings";
	private static File settingsFile;
	private static Context mContext;

	private static void createFile() {
		settingsFile = new File(mContext.getExternalFilesDir(null), filename);

		try {
			settingsFile.createNewFile();
		} catch (IOException e) {

		}
	}

	public static void setContext(Context context) {
		mContext = context;
	}

	public static void writeToFile(String name, String value) {
		try {
			if (settingsFile == null) {
				createFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					settingsFile, true));
			PrintWriter w = new PrintWriter(writer);
			w.println(name + ":" + value);
			writer.close();
			w.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readFromFile(String name) {
		try {
			if (settingsFile == null) {
				createFile();
			}
			BufferedReader writer = new BufferedReader(new FileReader(
					settingsFile));
			String line = "";
			while ((line = writer.readLine()) != null) {
				if (line.startsWith(name)) {
					line = line.substring(line.indexOf(':') + 1, line.length());
					break;
				}
			}
			writer.close();
			return line;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static boolean isFileExists() {
		settingsFile = new File(mContext.getExternalFilesDir(null), filename);
		return settingsFile.exists();
	}

	public static void storeSubscriptions(JSONArray array) {
		File subscriptionsfile = new File(mContext.getExternalFilesDir(null),
				"subscripitons");
		BufferedWriter writer;
		PrintWriter w;
		try {
			writer = new BufferedWriter(new FileWriter(subscriptionsfile));
			w = new PrintWriter(writer);
			w.print(array.toString());
			writer.close();
			w.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

	public static JSONArray readSubscriptions() {
		File subscriptionsfile = new File(mContext.getExternalFilesDir(null),
				"subscripitons");
		JSONArray array = null;
		BufferedReader reader;
		StringBuffer buffer = new StringBuffer();
		String line;
		try {
			reader = new BufferedReader(new FileReader(subscriptionsfile));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			array = new JSONArray(buffer.toString());
			reader.close();
		} catch (FileNotFoundException e) {

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			return array;
		}
	}

	public static void storeFeeds(String result) {
		// TODO Auto-generated method stub
		File feeds = new File(mContext.getExternalFilesDir(null), "feeds");
		BufferedWriter writer;
		PrintWriter w;
		try {
			writer = new BufferedWriter(new FileWriter(feeds));
			w = new PrintWriter(writer);
			w.print(result);
			writer.close();
			w.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readFeeds() {
		File feeds = new File(mContext.getExternalFilesDir(null), "feeds");
		BufferedReader reader;
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(feeds));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (FileNotFoundException e) {
			return null;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
