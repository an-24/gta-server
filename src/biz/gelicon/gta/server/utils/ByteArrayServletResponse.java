package biz.gelicon.gta.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ByteArrayServletResponse  extends HttpServletResponseStub  {
	private ServletOutputStreamImpl stream;
	private PrintWriter writer;
	private ServletOutputStreamImpl writerStream;

	public ByteArrayServletResponse() {
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws java.io.IOException {
		if(stream==null) {
			
            if (writer != null)
                throw new IllegalStateException(
                        "getWriter() has already been called for this response.");
			
			stream = new ServletOutputStreamImpl();
		}
		return stream;
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
        if (writer == null) {

            if (stream != null)
                throw new IllegalStateException(
                        "getOutputStream() has already been called for this response.");

            writerStream =  new ServletOutputStreamImpl();
            writer = new PrintWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            
        }
        return writer;
	}
	
	
    public byte[] toByteArray() throws IOException {
        return stream!=null?stream.toByteArray():writerStream.toByteArray();
    }

	class ServletOutputStreamImpl extends ServletOutputStream {

	    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

	    ServletOutputStreamImpl() {
	    }

	    public byte[] toByteArray() throws IOException {
	    	out.flush();
	        return out.toByteArray();
	    }

		@Override
	    public void write(int b) throws IOException {
	        out.write(b);
	    }

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener l) {
		}


	}
}
