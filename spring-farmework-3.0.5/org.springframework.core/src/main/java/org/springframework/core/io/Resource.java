/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Interface for a resource descriptor that abstracts from the actual
 * type of underlying resource, such as a file or class path resource.
 *
 * <p>An InputStream can be opened for every resource if it exists in
 * physical form, but a URL or File handle can just be returned for
 * certain resources. The actual behavior is implementation-specific.
 * <p>
 * 从实际类型的底层资源（例如文件或类路径资源）中抽象出来的资源描述符的接口。
 * 如果InputStream以物理形式存在，则可以为每个资源打开它，但只能为某些资源返回URL或File句柄。
 * 实际行为是特定于实现的。
 *
 * @author Juergen Hoeller
 * @see #getInputStream()
 * @see #getURL()
 * @see #getURI()
 * @see #getFile()
 * @see FileSystemResource
 * @see ClassPathResource
 * @see UrlResource
 * @see ByteArrayResource
 * @see InputStreamResource
 * @see org.springframework.web.context.support.ServletContextResource
 * @since 28.12.2003
 */
public interface Resource extends InputStreamSource {

    /**
     * Return whether this resource actually exists in physical form.
     * <p>This method performs a definitive existence check, whereas the
     * existence of a <code>Resource</code> handle only guarantees a
     * valid descriptor handle.
     */
    boolean exists();

    /**
     * Return whether the contents of this resource can be read,
     * e.g. via {@link #getInputStream()} or {@link #getFile()}.
     * <p>Will be <code>true</code> for typical resource descriptors;
     * note that actual content reading may still fail when attempted.
     * However, a value of <code>false</code> is a definitive indication
     * that the resource content cannot be read.
     */
    boolean isReadable();

    /**
     * Return whether this resource represents a handle with an open
     * stream. If true, the InputStream cannot be read multiple times,
     * and must be read and closed to avoid resource leaks.
     * <p>Will be <code>false</code> for typical resource descriptors.
     */
    boolean isOpen();

    /**
     * Return a URL handle for this resource.
     *
     * @throws IOException if the resource cannot be resolved as URL,
     *                     i.e. if the resource is not available as descriptor
     */
    URL getURL() throws IOException;

    /**
     * Return a URI handle for this resource.
     *
     * @throws IOException if the resource cannot be resolved as URI,
     *                     i.e. if the resource is not available as descriptor
     */
    URI getURI() throws IOException;

    /**
     * Return a File handle for this resource.
     *
     * @throws IOException if the resource cannot be resolved as absolute
     *                     file path, i.e. if the resource is not available in a file system
     */
    File getFile() throws IOException;

    /**
     * Determine the content length for this resource.
     *
     * @throws IOException if the resource cannot be resolved
     *                     (in the file system or as some other known physical resource type)
     */
    long contentLength() throws IOException;

    /**
     * Determine the last-modified timestamp for this resource.
     *
     * @throws IOException if the resource cannot be resolved
     *                     (in the file system or as some other known physical resource type)
     */
    long lastModified() throws IOException;

    /**
     * Create a resource relative to this resource.
     *
     * @param relativePath the relative path (relative to this resource)
     * @return the resource handle for the relative resource
     * @throws IOException if the relative resource cannot be determined
     */
    Resource createRelative(String relativePath) throws IOException;

    /**
     * Return a filename for this resource, i.e. typically the last
     * part of the path: for example, "myfile.txt".
     */
    String getFilename();

    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value
     * from their <code>toString</code> method.
     *
     * @see java.lang.Object#toString()
     */
    String getDescription();

}
