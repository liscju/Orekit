/* Copyright 2002-2008 CS Communication & Systèmes
 * Licensed to CS Communication & Systèmes (CS) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * CS licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.orekit.data;

import java.io.BufferedReader;

import org.orekit.data.DataDirectoryCrawler;
import org.orekit.data.DataFileCrawler;
import org.orekit.errors.OrekitException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataDirectoryCrawlerTest extends TestCase {

    public void testNoDirectory() {
        checkFailure("inexistant-directory");
    }

    public void testNotADirectory() {
        checkFailure("regular-data/UTC-TAI.history");
    }

    public void testNominal() throws OrekitException {
        System.setProperty(DataDirectoryCrawler.DATA_ROOT_DIRECTORY_CP, "regular-data");
        CountingCrawler crawler = new CountingCrawler(".*");
        new DataDirectoryCrawler().crawl(crawler);
        assertTrue(crawler.getCount() > 0);
    }

    private void checkFailure(String directoryName) {
        try {
            System.setProperty(DataDirectoryCrawler.DATA_ROOT_DIRECTORY_CP, directoryName);
            new DataDirectoryCrawler().crawl(new CountingCrawler(".*"));
            fail("an exception should have been thrown");
        } catch (OrekitException e) {
            // expected behavior
        } catch (Exception e) {
            e.printStackTrace();
            fail("wrong exception caught");
        }
    }

    private static class CountingCrawler extends DataFileCrawler {
        private int count;
        public CountingCrawler(String pattern) {
            super(pattern);
            count = 0;
        }
        protected void visit(BufferedReader reader) {
            ++count;
        }
        public int getCount() {
            return count;
        }
    }

    public static Test suite() {
        return new TestSuite(DataDirectoryCrawlerTest.class);
    }

}