/*
 * @(#)SafePrintWriter.java 1.0 99/07/10
 *
 * Written 1999 by Elliotte Rusty Harold,
 * Explicitly placed in the public domain
 * No rights reserved.
 */

package com.macfaq.io;

import java.io.*;

/**
 * 
 *
 * @version 1.0, 99/07/10
 * @author Elliotte Rusty Harold
 * @since Java Network Programming, 2nd edition
 */

public class SafePrintWriter extends Writer {

    protected Writer out;

    private boolean autoFlush = false;
    private String lineSeparator;
    private boolean closed = false;

    public SafePrintWriter(Writer out, String lineSeparator) {
        this(out, false, lineSeparator);
    }

    public SafePrintWriter(Writer out, char lineSeparator) {
        this(out, false, String.valueOf(lineSeparator));
    }

    public SafePrintWriter(Writer out, boolean autoFlush, String lineSeparator) {
        super(out);
        this.out = out;
        this.autoFlush = autoFlush;
        this.lineSeparator = lineSeparator;
    }

    public SafePrintWriter(OutputStream out, boolean autoFlush, String encoding, String lineSeparator)
            throws UnsupportedEncodingException {
        this(new OutputStreamWriter(out, encoding), autoFlush, lineSeparator);
    }

    public void flush() throws IOException {

        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            out.flush();
        }

    }

    public void close() throws IOException {

        try {
            this.flush();
        } catch (IOException e) {
        }

        synchronized (lock) {
            out.close();
            this.closed = true;
        }

    }

    public void write(int c) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            out.write(c);
        }
    }

    public void write(char[] text, int offset, int length) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            out.write(text, offset, length);
        }
    }

    public void write(char[] text) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            out.write(text, 0, text.length);
        }
    }

    public void write(String s, int offset, int length) throws IOException {

        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            out.write(s, offset, length);
        }

    }

    public void print(boolean b) throws IOException {
        if (b)
            this.write("true");
        else
            this.write("false");
    }

    public void println(boolean b) throws IOException {
        if (b)
            this.write("true");
        else
            this.write("false");
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(char c) throws IOException {
        this.write(String.valueOf(c));
    }

    public void println(char c) throws IOException {
        this.write(String.valueOf(c));
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(int i) throws IOException {
        this.write(String.valueOf(i));
    }

    public void println(int i) throws IOException {
        this.write(String.valueOf(i));
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(long l) throws IOException {
        this.write(String.valueOf(l));
    }

    public void println(long l) throws IOException {
        this.write(String.valueOf(l));
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(float f) throws IOException {
        this.write(String.valueOf(f));
    }

    public void println(float f) throws IOException {
        this.write(String.valueOf(f));
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(double d) throws IOException {
        this.write(String.valueOf(d));
    }

    public void println(double d) throws IOException {
        this.write(String.valueOf(d));
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(char[] text) throws IOException {
        this.write(text);
    }

    public void println(char[] text) throws IOException {
        this.write(text);
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(String s) throws IOException {
        if (s == null)
            this.write("null");
        else
            this.write(s);
    }

    public void println(String s) throws IOException {
        if (s == null)
            this.write("null");
        else
            this.write(s);
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void print(Object o) throws IOException {
        if (o == null)
            this.write("null");
        else
            this.write(o.toString());
    }

    public void println(Object o) throws IOException {
        if (o == null)
            this.write("null");
        else
            this.write(o.toString());
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

    public void println() throws IOException {
        this.write(lineSeparator);
        if (autoFlush)
            out.flush();
    }

}
