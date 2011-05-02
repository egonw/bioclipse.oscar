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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.bioclipse.cdk.business.CDKManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;
import org.openscience.cdk.io.formats.CMLFormat;
import org.openscience.cdk.io.formats.IChemFormat;

import uk.ac.cam.ch.wwmm.oscar.Oscar;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.ChemicalStructure;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.FormatType;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.ResolvedNamedEntity;
import uk.ac.cam.ch.wwmm.oscar.document.NamedEntity;

public class OscarManager implements IBioclipseManager {

    private static final Logger logger = Logger.getLogger(OscarManager.class);

    private Oscar oscar = new Oscar();
	private CDKManager cdk = new CDKManager();

    /**
     * Gives a short one word name of the manager used as variable name when
     * scripting.
     */
    public String getManagerName() {
        return "oscar";
    }

    public List<String> findNamedEntities(String text) {
    	List<String> mols = new ArrayList<String>();
    	List<NamedEntity> entities = oscar.findNamedEntities(text);
    	for (NamedEntity entity : entities) {
    		mols.add(entity.getSurface());
    	}
    	return mols;
    }

    public List<IMolecule> findResolvedNamedEntities(String text) {
    	List<IMolecule> mols = new ArrayList<IMolecule>();
    	List<ResolvedNamedEntity> entities = oscar.findAndResolveNamedEntities(text);
    	for (ResolvedNamedEntity entity : entities) {
    		ChemicalStructure structure =
    			entity.getFirstChemicalStructure(FormatType.CML);
    		if (structure != null) {
    			IMolecule mol;
				try {
					mol = cdk.loadMolecule(
						new ByteArrayInputStream(
							structure.getValue().getBytes()
						), (IChemFormat)CMLFormat.getInstance(), null
					);
	    		    mols.add(mol);
				} catch (BioclipseException e) {
					logger.error("Error while creating an IMolecule for an " +
							"extracted compound: " + e.getMessage());
					logger.debug(e);
				} catch (IOException e) {
					logger.error("Error while creating an IMolecule for an " +
							"extracted compound: " + e.getMessage());
					logger.debug(e);
				}
    		}
    	}
    	return mols;
    }
}
