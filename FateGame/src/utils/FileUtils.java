package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return realPath
	 */
	public static String MakeDirs(String path)
	{
		File f = new File(path);
		if (!f.exists())
		{
			f.mkdirs();
		}
		return f.getPath();
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean hasFile(String path)
	{
		File f = new File(path);
		return f.exists();
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @return find the file type
	 */
	public static String getFileType(String fileName)
	{
		int pos = fileName.lastIndexOf(".");
		if (pos < 0)
		{
			return "";
		}
		else
		{
			return fileName.substring(pos + 1);
		}
	}

	/**
	 * 文件拷贝
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void copyFile(File in, File out) throws Exception
	{
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1)
		{
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 * @param encoding
	 * @return file content
	 */
	public static String readStringFile(String fileName, String encoding)
			throws Exception
	{
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis,
				encoding));
		sb = new StringBuffer();
		while (reader.ready())
		{
			String line = reader.readLine();
			sb.append(line);
			sb.append("\r\n");
		}
		reader.close();
		fis.close();
		return sb.toString();
	}

	/**
	 * 以二进制方式读取文件内容
	 * 
	 * @param fileName
	 * @return file content
	 */
	public static byte[] readBytesFile(String fileName)
	{
		byte[] bytes = new byte[0];
		FileInputStream fis;
		try
		{
			fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			int leng = bis.available();
			bytes = new byte[leng];
			bis.read(bytes);
			fis.close();
			bis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 读取文件内容，读取格式为UTF-8
	 * 
	 * @param fileName
	 * @return file content
	 */
	public static String readStringFileByUTF(String fileName) throws Exception
	{
		return readStringFile(fileName, "UTF-8");
	}

	/**
	 * 读取文件内容，读取格式为GBK
	 * 
	 * @param fileName
	 * @return file content
	 */
	public static String readStringFileByGBK(String fileName) throws Exception
	{
		return readStringFile(fileName, "GBK");
	}

	/**
	 * 读取文件内容，读取格式为ISO-8859-1
	 * 
	 * @param fileName
	 * @return file content
	 */
	public static String readStringFileByISO(String fileName) throws Exception
	{
		return readStringFile(fileName, "ISO-8859-1");
	}

	/**
	 * 得到文件路径
	 * 
	 * @param file
	 * @return file real path
	 */
	public static String getFileNamePath(File file)
	{
		return getFileNamePath(file.getAbsolutePath());
	}

	/**
	 * 得到文件路径
	 * 
	 * @param fileName
	 * @return file real path
	 */
	public static String getFileNamePath(String fileName)
	{
		int pos = fileName.lastIndexOf("\\");
		int pos2 = fileName.lastIndexOf("/");
		if (pos == -1 && pos2 == -1)
		{
			return "";
		}
		else
		{
			if (pos2 > pos)
			{
				return fileName.substring(0, pos2);
			}
			else
			{
				return fileName.substring(0, pos);
			}
		}
	}

	/**
	 * 在文件末尾追加内容
	 * 
	 * @param addContent
	 * @param fileName
	 * @param encoding
	 * @return add access or fail
	 */
	public static boolean addWriteStringFile(String addContent,
			String fileName, String encoding) throws Exception
	{
		String s = readStringFile(fileName, encoding);
		s = s + addContent;
		return writeStringFile(s, fileName, encoding);
	}

	/**
	 * 将内容写入文件里
	 * 
	 * @param fileContent
	 * @param fileName
	 * @param encoding
	 * @return add access or fail
	 */
	public static boolean writeStringFile(String fileContent, String fileName,
			String encoding)
	{
		try
		{
			MakeDirs(getFileNamePath(fileName));
			File file = new File(fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] b = fileContent.getBytes(encoding);
			fileOutputStream.write(b);
			fileOutputStream.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 持贝指定的目录下所有文件及子目录到目标文件夹
	 * 
	 * @param file
	 * @param tofile
	 */
	public static void CopyFolder(File file, File tofile)
	{
		MakeDirs(getFileNamePath(tofile));
		// 获取源目录下一级所有目录文件
		File[] files = file.listFiles();
		// 逐个判断，创建目录，执行递归调用
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				File copyPath = new File(tofile.getAbsolutePath() + "\\"
						+ files[i].getName());
				copyPath.mkdir();
				CopyFolder(files[i], copyPath);
			}
			else
			{ // 如果file为文件，读取字节流写入目标文件;
				try
				{
					FileInputStream fiStream = new FileInputStream(files[i]);
					BufferedInputStream biStream = new BufferedInputStream(
							fiStream);
					File copyFile = new File(tofile.getAbsolutePath() + "\\"
							+ files[i].getName());
					copyFile.createNewFile();
					FileOutputStream foStream = new FileOutputStream(copyFile);
					BufferedOutputStream boStream = new BufferedOutputStream(
							foStream);
					int j;
					while ((j = biStream.read()) != -1)
					{
						boStream.write(j);
					}
					/* 关闭流 */
					biStream.close();
					boStream.close();
					fiStream.close();
					foStream.close();
				}
				catch (FileNotFoundException ex)
				{
					ex.printStackTrace();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取文件夹中所有文件名(不包括文件夹中的子目录)
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readDirsAllFiles(String filePath)
	{
		List<String> fileNameList = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (files != null)
		{
			for (int i = 0; i < files.length; i++)
			{
				if (!files[i].isDirectory())
				{
					File file = files[i];
					int pos = file.getName().lastIndexOf(".");
					if (pos < 0)
					{
						fileNameList.add(file.getName());
					}
					else
					{
						fileNameList.add(file.getName().substring(0, pos));
					}
				}
			}
		}
		return fileNameList;
	}

	/**
	 * 转换文件大小为KB
	 * 
	 * @param fileLength
	 * @return file KB size
	 */
	public static Float getFileSizeKB(Long fileLength)
	{
		return new Float(fileLength / 1024);
	}

	/**
	 * 转换文件大小为MB
	 * 
	 * @param fileLength
	 * @return file MB size
	 */
	public static Float getFileSizeMB(Long fileLength)
	{
		return new Float(fileLength / 1024 / 1024);
	}

	/**
	 * 转换文件大小为KB
	 * 
	 * @param file
	 * @return file KB size
	 */
	public static Float getFileSizeKB(File file)
	{
		return getFileSizeKB(file.length());
	}

	/**
	 * 转换文件大小为MB
	 * 
	 * @param file
	 * @return file MB size
	 */
	public static Float getFileSizeMB(File file)
	{
		return getFileSizeMB(file.length());
	}
	
	
	/** 将一个字符串写入到指定的文本文件中，自动创建目录，默认为重写方式 */
	public static void string2File(String fileName, String str)
			throws IOException
	{
		string2File(fileName, str, false);
	}

	/** 将一个字符串写入到指定的文本文件中，自动创建目录，参数append为是否追加方式 */
	public static void string2File(String fileName, String str, boolean append)
			throws IOException
	{
		File file = new File(fileName);
		String parent = file.getParent();
		if (parent != null)
		{
			File tree = new File(parent);
			if (!tree.exists())
				tree.mkdirs();
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(fileName, append);
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.flush();
		}
		finally
		{
			try
			{
				if (fw != null)
					fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** 将一个文本文件中的内容读出，返回一个字符串，每行之间用换行符“\n”隔开 */
	public static String file2String(String fileName) throws IOException
	{
		return file2String(fileName, true);
	}

	/** 将一个文本文件中的内容读出，返回一个字符串，separator表示是否每行之间用换行符“\n”隔开 */
	public static String file2String(String fileName, boolean separator)
			throws IOException
	{
		StringBuffer sb = new StringBuffer();
		FileReader fr = null;
		try
		{
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
				if (separator)
					sb.append("\n");
			}
			if (sb.length() > 0)
				sb.setLength(sb.length() - 1);
			return sb.toString();
		}
		finally
		{
			try
			{
				if (fr != null)
					fr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** 将一个文本文件中的内容读出，返回一个字符串数组，字符串数组中的每一个元素为文件的一行 */
	public static String[] file2StringArray(String fileName) throws IOException
	{
		ArrayList<String> strList = new ArrayList<String>();
		FileReader fr = null;
		try
		{
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null)
				strList.add(line);
			String[] strs = new String[strList.size()];
			strList.toArray(strs);
			return strs;
		}
		finally
		{
			try
			{
				if (fr != null)
					fr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** 将一个文件以二进制数据方式读出 */
	public static byte[] file2ByteArray(String fileName) throws IOException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			// 得到文件大小
			int len = bis.available();
			byte[] buffer = new byte[len];
			bis.read(buffer);
			return buffer;
		}
		finally
		{
			try
			{
				if (fis != null)
					fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，默认为重写方式 */
	public static void byteArray2File(String fileName, byte[] data)
			throws IOException
	{
		byteArray2File(fileName, data, 0, data.length, false);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，默认为重写方式 */
	public static void byteArray2File(String fileName, byte[] data, int offset,
			int len) throws IOException
	{
		byteArray2File(fileName, data, offset, len, false);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，参数append为是否追加方式 */
	public static void byteArray2File(String fileName, byte[] data,
			boolean append) throws IOException
	{
		byteArray2File(fileName, data, 0, data.length, append);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，参数append为是否追加方式 */
	public static void byteArray2File(String fileName, byte[] data, int offset,
			int len, boolean append) throws IOException
	{
		if (offset < 0 || offset >= data.length)
			throw new IllegalArgumentException(
					"FileKit byteArray2File, invalid offset:" + offset);
		if (len <= 0 || offset + len > data.length)
			throw new IllegalArgumentException(
					"FileKit byteArray2File, invalid length:" + len);
		File file = new File(fileName);
		String parent = file.getParent();
		if (parent != null)
		{
			File tree = new File(parent);
			if (!tree.exists())
				tree.mkdirs();
		}
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try
		{
			fos = new FileOutputStream(file, append);
			bos = new BufferedOutputStream(fos);
			bos.write(data, offset, len);
			bos.flush();
		}
		finally
		{
			try
			{
				if (fos != null)
					fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录）
	 */
	public static String[] listFiles(String directory)
	{
		return listFiles(directory, true);
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录） 参数b表示返回的数组中是否包含目录
	 */
	public static String[] listFiles(String directory, boolean b)
	{
		return listFiles(directory, b, null);
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录） 参数b表示返回的数组中是否包含目录
	 */
	public static String[] listFiles(String directory, boolean b, String nofile)
	{
		File file = new File(directory);
		if (!file.exists())
			return null;
		if (!file.isDirectory())
			return null;
		ArrayList<String> fileList = new ArrayList<String>();
		listFiles(file, "", fileList, b, nofile);
		String[] strs = new String[fileList.size()];
		fileList.toArray(strs);
		return strs;
	}

	/** 遍历指定目录中所有的文件和目录，结果存放到向量中 */
	private static void listFiles(File directory, String path,
			ArrayList<String> fileList, boolean b, String nofile)
	{
		listFilesFilter(directory, path, fileList, b, nofile);
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录）
	 */
	public static String[] listFiles(String directory, String nofile)
	{
		return listFiles(directory, true, nofile);
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录）
	 */
	public static String[] listFiles(String directory, String fileter,
			String nofile)
	{
		return listFiles(directory, true, nofile);
	}

	/** 遍历指定目录中所有的文件和目录，结果存放到向量中 */
	private static void listFilesFilter(File directory, String path,
			ArrayList<String> fileList, boolean b, String nofile)
	{
		File[] files = directory.listFiles();
		if (files == null)
			return;
		String name;
		for (int i = 0; i < files.length; i++)
		{
			name = path + files[i].getName();
			if (nofile != null && name.indexOf(nofile) > 0)
				continue;
			if (files[i].isDirectory())
			{
				if (b)
					fileList.add(name);
				listFiles(files[i], name + File.separator, fileList, b, nofile);
			}
			else
				fileList.add(name);
		}
	}

	/**
	 * 遍历指定目录中所有的文件和目录， 返回的字符串数组包含该目录中所有文件和目录的相对地址（相对于该目录） 参数b表示返回的数组中是否包含目录
	 */
	public static File[] getFiles(String directory, boolean b)
	{
		File file = new File(directory);
		if (!file.exists())
			return null;
		if (!file.isDirectory())
			return null;
		ArrayList<File> fileList = new ArrayList<File>();
		getFiles(file, fileList, b);
		File[] files = new File[fileList.size()];
		fileList.toArray(files);
		return files;
	}

	/** 遍历指定目录中所有的文件和目录，结果存放到向量中 */
	private static void getFiles(File directory, ArrayList<File> fileList,
			boolean b)
	{
		File[] files = directory.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				if (b)
					fileList.add(files[i]);
				getFiles(files[i], fileList, b);
			}
			else
				fileList.add(files[i]);
		}
	}
	
}
