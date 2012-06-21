/*
 * Copyright (C) 2011 Benoit GUEROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jongo.hotfixes;

import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.DateDeserializer;
import org.jongo.marshall.jackson.DateSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static org.jongo.util.TestUtil.createEmptyCollection;
import static org.jongo.util.TestUtil.dropCollection;

public class DateSerialisationTest {

    private MongoCollection collection;
    private Date meetingDate;
    private ObjectId meetingId;

    @Before
    public void setUp() throws Exception {
        collection = createEmptyCollection("bugfixes");
        meetingDate = new Date();
        meetingId = new ObjectId(collection.save(new Meeting(meetingDate)));
    }

    @After
    public void tearDown() throws Exception {
        dropCollection("bugfixes");
    }

    @Test
    public void shouldSerializeISODate() throws IOException {

        DBObject result = collection.getDBCollection().findOne(QueryBuilder.start("date").exists(true).get());
        assertThat(result.toString()).contains("$date");
    }

    @Test
    public void shouldDeserializeISODate() throws IOException {

        Meeting m = collection.findOne(meetingId).as(Meeting.class);
        assertThat(m.date).isEqualTo(meetingDate);
    }


    private static class Meeting {
        @JsonSerialize(using = DateSerializer.class)
        @JsonDeserialize(using = DateDeserializer.class)
        private final Date date;

        @JsonCreator
        public Meeting(@JsonProperty("date") Date meetingDate) {
            this.date = meetingDate;
        }
    }
}
