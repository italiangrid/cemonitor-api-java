/*
 * Copyright (c) Members of the EGEE Collaboration. 2004. 
 * See http://www.eu-egee.org/partners/ for details on the copyright
 * holders.  
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
 
/*
 *
 * Author Luigi Zangrando <zangrando@pd.infn.it>
 *
 */

package org.glite.ce.monitorapij.resource;

import java.io.File;
import java.io.FilenameFilter;


/**
 * This class checks if a file located in the specified directory is a jar.
 * @see java.io.FilenameFilter
 */
public class ClassFilter implements FilenameFilter {
    /**
     * Check if a file located in the specified directory is a jar.
     * @param dir File specifying a directory (not yet used).
     * @param name String specifying the name of the file to check.
     * @return true if the file specified by the String name ends with ".jar", false otherwise.
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File dir, String name) {
        return (name.endsWith(".jar"));
    }
}
