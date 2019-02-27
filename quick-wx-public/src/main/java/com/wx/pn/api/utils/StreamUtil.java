package com.wx.pn.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class StreamUtil {

	private static final Logger LOG = LoggerFactory.getLogger(StreamUtil.class);

	private StreamUtil() {
	}

	/**
	 * 将输入流的内容复制到输出流里
	 *
	 * @param in  输入流
	 * @param out 输出流
	 * @return 复制的数据字节数
	 * @throws IOException IO异常
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		BeanUtil.requireNonNull(in, "No InputStream specified");
		BeanUtil.requireNonNull(out, "No OutputStream specified");
		int byteCount = 0;
		byte[] buffer = new byte[4096];
		int bytesRead1;
		for (; (bytesRead1 = in.read(buffer)) != -1; byteCount += bytesRead1) {
			out.write(buffer, 0, bytesRead1);
		}
		out.flush();
		return byteCount;
	}

	/**
	 * 关闭需要关闭的对象，如果关闭出错，给出警告
	 *
	 * @param closeable 需要关闭的对象
	 */
	public static void closeWithWarn(Closeable closeable) {
		if (BeanUtil.nonNull(closeable)) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOG.warn("关闭流出错......", e);
			}
		}
	}
}
