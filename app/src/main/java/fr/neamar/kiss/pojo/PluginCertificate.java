package fr.neamar.kiss.pojo;

public class PluginCertificate {
    private String issuer;
    private String signature;

    public boolean validate() {
        // TODO: Implement certificate validation logic
        return true;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}