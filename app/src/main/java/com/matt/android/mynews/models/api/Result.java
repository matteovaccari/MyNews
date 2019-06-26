package com.matt.android.mynews.models.api;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class to manage api content getters and setters
 */

public class Result {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("email_count")
    @Expose
    private Integer emailCount;
    @SerializedName("count_type")
    @Expose
    private String countType;
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("eta_id")
    @Expose
    private Long etaId;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("asset_id")
    @Expose
    private Long assetId;
    @SerializedName("nytdsection")
    @Expose
    private String nytdsection;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    @SerializedName("uri")
    @Expose
    private String uri;
    @Expose
    private List<MultiMedium> multimedia = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public Integer getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public Object getColumn() {
        return column;
    }

    public void setColumn(Object column) {
        this.column = column;
    }

    public Long getEtaId() {
        return etaId;
    }

    public void setEtaId(Long etaId) {
        this.etaId = etaId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getNytdsection() {
        return nytdsection;
    }

    public void setNytdsection(String nytdsection) {
        this.nytdsection = nytdsection;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<MultiMedium> getMultimedia() {
        return multimedia;
    }

    //Search

    @SerializedName("pub_date")
    @Expose
    private Date published_date;

    @SerializedName("headline")
    @Expose
    private Result headline;

    @SerializedName("main")
    @Expose
    private String main;

    //List of news if NewsObject is from Article Search Api
    @SerializedName("docs")
    @Expose
    private ArrayList<Result> docs;

    //Number of hits from Article Search
    @SerializedName("meta")
    @Expose
    private Result meta;

    @SerializedName("hits")
    @Expose
    public int hits;


    public int getHits() {
        return hits;
    }

    public String getImageUrl() {
        try {
            return multimedia.get(0).getUrl();
        } catch (Exception e) {
            return null;
        }
    }

    public Result getMeta() {
        return meta;
    }

    ArrayList<Result> getDocs() {
        return docs;
    }

    public String sectionAndSubsectionString() {
        String str = "";
        if (section != null && subsection != null && !subsection.equals("false") && !subsection.equals(section)) {
            str += section + " > " + subsection;
        } else if (section != null) {
            str += section;
        } else if (headline.getMain() != null) {
            str += headline.getMain();
        }
        return str;
    }

    void setMultimedia(ArrayList<MultiMedium> multimedia) {
        this.multimedia = multimedia;
    }

    private String getMain() {
        return main;
    }

    void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }

    void setMain(String main) {
        this.main = main;
    }

    void setHeadline(Result headline) {
        this.headline = headline;
    }

}
