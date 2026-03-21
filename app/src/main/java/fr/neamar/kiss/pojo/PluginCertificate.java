package fr.neamar.kiss.pojo;

/**
 * Represents the certificate of a Viabhron plugin.
 */
public class PluginCertificate {
    private String issuer;
    private String signature;

    public PluginCertificate() {}

    public PluginCertificate(String issuer, String signature) {
        this.issuer = issuer;
        this.signature = signature;
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

    /**
     * Validates the certificate.
     * @return true if the certificate is valid, false otherwise
     */
    public boolean validate() {
        // In a real implementation, this would verify the signature against a public key
        return issuer != null && !issuer.isEmpty() && signature != null && !signature.isEmpty();
    }

    @Override
    public String toString() {
        return "PluginCertificate{" +
                "issuer='" + issuer + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}