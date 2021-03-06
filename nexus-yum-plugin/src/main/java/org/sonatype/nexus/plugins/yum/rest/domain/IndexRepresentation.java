/**
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.yum.rest.domain;

import java.io.File;
import org.restlet.data.MediaType;
import org.restlet.resource.StringRepresentation;
import org.sonatype.nexus.plugins.yum.repository.FileDirectoryStructure;

public class IndexRepresentation
    extends StringRepresentation
{
    public IndexRepresentation( UrlPathInterpretation interpretation, FileDirectoryStructure fileDirectoryStructure )
    {
        super( generateRepoIndex( fileDirectoryStructure, interpretation ) );
        setMediaType( MediaType.TEXT_HTML );
    }

    private static CharSequence generateRepoIndex( FileDirectoryStructure fileDirectoryStructure,
                                                   UrlPathInterpretation interpretation )
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "<html><head><title>File list</title></head><body><ul>" );

        File directory = fileDirectoryStructure.getFile( interpretation.getPath() );

        appendFiles( builder, directory.listFiles() );

        builder.append( "</ul></html>" );
        return builder.toString();
    }

    private static void appendFiles( StringBuilder builder, File[] files )
    {
        for ( File file : files )
        {
            String name = file.getName();
            if ( file.isDirectory() )
            {
                name += "/";
            }
            builder.append( String.format( "<li><a href=\"%s\">%s</a></li>", name, name ) );
        }
    }
}
