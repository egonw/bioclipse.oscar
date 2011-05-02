/*******************************************************************************
 * Copyright (c) 2011  Egon Willighagen <egon.willighagen@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.oscar.business;

import java.util.List;

import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.managers.business.IBioclipseManager;

@PublishedClass(
    value="Adds text mining functionality to Bioclipse."
)
public interface IOscarManager extends IBioclipseManager {

    @Recorded
    @PublishedMethod(
        methodSummary="Extracts molecules from a text.",
        params="String text"
    )
    public List<String> findNamedEntities(String text);

    @Recorded
    @PublishedMethod(
        methodSummary="Extracts molecules from a text.",
        params="String text"
    )
    public List<IMolecule> findResolvedNamedEntities(String text);

}
