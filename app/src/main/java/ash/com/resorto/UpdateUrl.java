package ash.com.resorto;

/**
 * Created by AsH on 24/10/16.
 */
public class UpdateUrl {


    public UpdateUrl() {

        categoryUrl();
    }

    public String locationUrl(String locality, double lat, double lon, int count) {

        String xx = "https://developers.zomato.com/api/v2.1/locations?query=";

        String locationUrl = xx + locality + "&lat=" + lat + "&lon=" + lon + "&count=" + count;

        return locationUrl;

    }

    public String cuisineUrl(int cityId, double lat, double lon) {

        String yy = "https://developers.zomato.com/api/v2.1/cuisines?city_id=";
        String cuisineUrl = yy + cityId + "&lat=" + lat + "&lon=" + lon;
        return cuisineUrl;

    }


    public String establishmentUrl(int cityId, double lat, double lon) {

        String zz = "https://developers.zomato.com/api/v2.1/establishments?city_id=";
        String establishmentUrl = zz + cityId + "&lat=" + lat + "&lon=" + lon;
        return establishmentUrl;
    }

    public String collectionUrl(int cityId, double lat, double lon, int count) {

        String aa = "https://developers.zomato.com/api/v2.1/collections?city_id=";
        String collectionsUrl = aa + cityId + "&lat=" + lat + "&lon=" + lon + "&count=" + count;
        return collectionsUrl;

    }

    public String categoryUrl() {
        String bb = "https://developers.zomato.com/api/v2.1/categories";
        return bb;

    }

    public String searchUrl(int entityId, String entityType, int start, int count, double lat, double lon, double radius, String cuisines, String establishment, String collecton, String category, String sort, String order) {

        String cc = "https://developers.zomato.com/api/v2.1/search?entity_id=";
        String searchUrl = cc + entityId + "&entity_type=" + entityType + "&start=" + start + "&count=" + count + "&lat=" + lat + "&lon=" + lon + "&radius=" + radius +
                "&cuisines=" + cuisines + "&establishment_type=" + establishment + "&collection_id=" + collecton + "&category=" + category + "&sort=" + sort
                + "&order=" + order;

        return searchUrl;

    }

}
