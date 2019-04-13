package com.example.bookmanager.model;

import java.io.Serializable;

/**
 * Guide info entity class
 */
public class AboutInfo extends BaseBean implements Serializable {

    private String introduce;
    private String brandStory;
    private String developingFootprints;

    public AboutInfo() {
    }

    public AboutInfo(String introduce, String brandStory, String developingFootprints) {
        this.introduce = introduce;
        this.brandStory = brandStory;
        this.developingFootprints = developingFootprints;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getBrandStory() {
        return brandStory;
    }

    public void setBrandStory(String brandStory) {
        this.brandStory = brandStory;
    }

    public String getDevelopingFootprints() {
        return developingFootprints;
    }

    public void setDevelopingFootprints(String developingFootprints) {
        this.developingFootprints = developingFootprints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AboutInfo aboutInfo = (AboutInfo) o;

        if (introduce != null ? !introduce.equals(aboutInfo.introduce) : aboutInfo.introduce != null)
            return false;
        if (brandStory != null ? !brandStory.equals(aboutInfo.brandStory) : aboutInfo.brandStory != null)
            return false;
        return developingFootprints != null ? developingFootprints.equals(aboutInfo.developingFootprints) : aboutInfo.developingFootprints == null;
    }

    @Override
    public int hashCode() {
        int result = introduce != null ? introduce.hashCode() : 0;
        result = 31 * result + (brandStory != null ? brandStory.hashCode() : 0);
        result = 31 * result + (developingFootprints != null ? developingFootprints.hashCode() : 0);
        return result;
    }
}
