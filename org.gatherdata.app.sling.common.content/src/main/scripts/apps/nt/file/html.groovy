InputStream stream = resource.adaptTo(InputStream.class);
if (stream != null) {
    streamReader = new InputStreamReader(stream);
    char[] cbuf = new char[1024];
    int rd;
    while ((rd = streamReader.read(cbuf)) >= 0) {
        out.write(cbuf, 0, rd);
    }
}

