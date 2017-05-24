package com.lf.tempcore.tempModule.tempRemotComm;

/**
 * Created by longf on 2016/8/26.
 */
public class TempRemotAPIConfiguration {
    private final String mBaseUri;

    public TempRemotAPIConfiguration(TempRemptAPIBuilder builder) {
        mBaseUri = builder.getBaseUri();
    }

    public static class TempRemptAPIBuilder{
        private final String baseUri;

        public TempRemptAPIBuilder(String baseUri) {
            this.baseUri = baseUri;
        }

        public String getBaseUri() {
            return baseUri;
        }

    }

    public String getBaseUri() {
        return mBaseUri;
    }
}
