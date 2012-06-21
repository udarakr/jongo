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

package org.jongo.marshall.jackson;


import org.codehaus.jackson.JsonGenerator;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DateSerializerTest {

    private DateSerializer dateSerializer = new DateSerializer();

    @Test
    public void shouldSerializeDateUsingDriver() throws Exception {
        Date date = new Date(1340303152854L);
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);

        dateSerializer.serialize(date, jsonGenerator, null);

        verify(jsonGenerator).writeRawValue("{ \"$date\" : \"2012-06-21T18:25:52.854Z\"}");
    }
}
