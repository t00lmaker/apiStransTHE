package com.equalsp.stransthe;

import java.io.IOException;

public interface CachedServiceFileHander {
	public String loadCacheFile() throws IOException;
	public void saveCacheFile(String content)throws IOException;
}
