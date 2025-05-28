package jp.co.ricoh.cotos.commonlib.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class BufferedServletRequestWrapper extends HttpServletRequestWrapper {

	private byte[] buffer;

	public BufferedServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buff[] = new byte[1024];
		int read;
		while ((read = is.read(buff)) > 0) {
			baos.write(buff, 0, read);
		}

		this.buffer = baos.toByteArray();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new BufferedServletInputStream(this.buffer);
	}
}
