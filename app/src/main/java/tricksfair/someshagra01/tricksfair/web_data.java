package tricksfair.someshagra01.tricksfair;

import com.google.firebase.database.IgnoreExtraProperties;
    @IgnoreExtraProperties
public class web_data {

        public String Site1;
        public String Site2;
        public String Site3;
        public String Site4;
        public String Site5;
        public String Site6;
        public String Site7;
        public String Site8;
        public String Site9;
        public String Site10;


        public web_data() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }


        public web_data(String Site1, String Site2, String Site3, String Site4, String Site5, String Site6,
                        String Site7, String Site8, String Site9, String Site10) {
            this.Site1 = Site1;
            this.Site2 = Site2;
            this.Site3 = Site3;
            this.Site4 = Site4;
            this.Site5 = Site5;
            this.Site6 = Site6;
            this.Site7 = Site7;
            this.Site8 = Site8;
            this.Site9 = Site9;
            this.Site10 = Site10;
        }

    }