package com.example.lfy.spendbainews.entity;

import java.io.Serializable;
import java.util.List;

/**
 *百度Map定位
 */

public class BMapAdress implements Serializable{

    /**
     * status : 0
     * result : {"location":{"lng":104.06792333333327,"lat":30.67994153657952},"formatted_address":"四川省成都市青羊区王家塘街84号","business":"骡马市,新华西路,八宝街","addressComponent":{"country":"中国","country_code":0,"province":"四川省","city":"成都市","district":"青羊区","adcode":"510105","street":"王家塘街","street_number":"84号","direction":"附近","distance":"7"},"pois":[],"roads":[],"poiRegions":[],"sematic_description":"成都华氏陶瓷艺术博物馆附近0米","cityCode":75}
     */

    private int status;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"lng":104.06792333333327,"lat":30.67994153657952}
         * formatted_address : 四川省成都市青羊区王家塘街84号
         * business : 骡马市,新华西路,八宝街
         * addressComponent : {"country":"中国","country_code":0,"province":"四川省","city":"成都市","district":"青羊区","adcode":"510105","street":"王家塘街","street_number":"84号","direction":"附近","distance":"7"}
         * pois : []
         * roads : []
         * poiRegions : []
         * sematic_description : 成都华氏陶瓷艺术博物馆附近0米
         * cityCode : 75
         */

        private LocationBean location;
        private String formatted_address;
        private String business;
        private AddressComponentBean addressComponent;
        private String sematic_description;
        private int cityCode;
        private List<?> pois;
        private List<?> roads;
        private List<?> poiRegions;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getSematic_description() {
            return sematic_description;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public List<?> getPois() {
            return pois;
        }

        public void setPois(List<?> pois) {
            this.pois = pois;
        }

        public List<?> getRoads() {
            return roads;
        }

        public void setRoads(List<?> roads) {
            this.roads = roads;
        }

        public List<?> getPoiRegions() {
            return poiRegions;
        }

        public void setPoiRegions(List<?> poiRegions) {
            this.poiRegions = poiRegions;
        }

        public static class LocationBean {
            /**
             * lng : 104.06792333333327
             * lat : 30.67994153657952
             */

            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AddressComponentBean {
            /**
             * country : 中国
             * country_code : 0
             * province : 四川省
             * city : 成都市
             * district : 青羊区
             * adcode : 510105
             * street : 王家塘街
             * street_number : 84号
             * direction : 附近
             * distance : 7
             */

            private String country;
            private int country_code;
            private String province;
            private String city;
            private String district;
            private String adcode;
            private String street;
            private String street_number;
            private String direction;
            private String distance;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public int getCountry_code() {
                return country_code;
            }

            public void setCountry_code(int country_code) {
                this.country_code = country_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
