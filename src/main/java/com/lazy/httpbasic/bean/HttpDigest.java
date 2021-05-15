package com.lazy.httpbasic.bean;


import com.lazy.httpbasic.conf.Config;
import com.lazy.httpbasic.util.MD5Util;
import com.lazy.httpbasic.util.StringUtil;

import java.util.Locale;

public class HttpDigest {

    private String username;
    private String nonce;
    private String realm;
    private String qop;
    private String nc;
    private String cnonce;
    private String response;
    private String uri;
    private String stale;
    private String rspauth;
    private String algorithm;
    private String method;
    private String nextnonce;

    public String getNextnonce() {
        return nextnonce;
    }

    public void setNextnonce(String nextnonce) {
        this.nextnonce = nextnonce;
    }

    public String md5() {
        String a1 = this.getUsername() + ":" + this.getRealm() + ":" + Config.CONSOLE_HTTP_BASIC_PASSWORD;
        String ha1 = MD5Util.encodeByMD5(a1);

        String a2 = this.getMethod() + ":" + this.getUri();
        String ha2 = MD5Util.encodeByMD5(a2);
        //服务器计算出的摘要
        String responseBefore = ha1 + ":" + this.getNonce() + ":" + this.getNc()
                + ":" + this.getCnonce() + ":" + this.getQop() + ":" + ha2;
        String responseMD5 = MD5Util.encodeByMD5(responseBefore);
        return responseMD5;
    }

    public void parse(String str) {
        String[] pair = str.split(",");
        for (String s : pair) {
            String[] kv = s.split("=");
            if (kv.length != 2) {
                continue;
            }
            String k = kv[0].toLowerCase(Locale.ENGLISH).trim();
            String v = kv[1];
            if ("username".equals(k)) {
                this.username = v;
            }
            if ("nonce".equals(k)) {
                this.nonce = v;
            }
            if ("realm".equals(k)) {
                this.realm = v;
            }
            if ("qop".equals(k)) {
                this.qop = v;
            }
            if ("nc".equals(k)) {
                this.nc = v;
            }
            if ("cnonce".equals(k)) {
                this.cnonce = v;
            }
            if ("response".equals(k)) {
                this.response = v;
            }
            if ("uri".equals(k)) {
                this.uri = v;
            }
            if ("stale".equals(k)) {
                this.stale = v;
            }
            if ("rspauth".equals(k)) {
                this.rspauth = v;
            }
            if ("algorithm".equals(k)) {
                this.algorithm = v;
            }
            if ("nextnonce".equals(k)) {
                this.nextnonce = v;
            }
        }
    }

    public String ofString() {
        StringBuilder s = new StringBuilder("Digest ");
        if (StringUtil.isNotBlank(username)) {
            s.append("username=").append(username).append(",");
        }
        if (StringUtil.isNotBlank(nonce)) {
            s.append("nonce=").append(nonce).append(",");
        }
        if (StringUtil.isNotBlank(realm)) {
            s.append("realm=").append(realm).append(",");
        }
        if (StringUtil.isNotBlank(qop)) {
            s.append("qop=").append(qop).append(",");
        }
        if (StringUtil.isNotBlank(nc)) {
            s.append("nc=").append(nc).append(",");
        }
        if (StringUtil.isNotBlank(cnonce)) {
            s.append("cnonce=").append(cnonce).append(",");
        }
        if (StringUtil.isNotBlank(response)) {
            s.append("response=").append(response).append(",");
        }
        if (StringUtil.isNotBlank(uri)) {
            s.append("uri=").append(uri).append(",");
        }
        if (StringUtil.isNotBlank(stale)) {
            s.append("stale=").append(stale).append(",");
        }
        if (StringUtil.isNotBlank(rspauth)) {
            s.append("rspauth=").append(rspauth).append(",");
        }
        if (StringUtil.isNotBlank(algorithm)) {
            s.append("algorithm=").append(algorithm).append(",");
        }
        if (StringUtil.isNotBlank(nextnonce)) {
            s.append("nextnonce=").append(nextnonce).append(",");
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRspauth() {
        return rspauth;
    }

    public void setRspauth(String rspauth) {
        this.rspauth = rspauth;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getStale() {
        return stale;
    }

    public void setStale(String stale) {
        this.stale = stale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getQop() {
        return qop;
    }

    public void setQop(String qop) {
        this.qop = qop;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getCnonce() {
        return cnonce;
    }

    public void setCnonce(String cnonce) {
        this.cnonce = cnonce;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}
