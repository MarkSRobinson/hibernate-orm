/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.metamodel.internal.source.hbm;

import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToManyElement;
import org.hibernate.internal.jaxb.mapping.hbm.PluralAttributeElement;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.metamodel.spi.source.LocalBindingContext;
import org.hibernate.metamodel.spi.source.OneToManyPluralAttributeElementSource;
import org.hibernate.metamodel.spi.source.PluralAttributeElementNature;

/**
 * @author Steve Ebersole
 */
public class OneToManyPluralAttributeElementSourceImpl implements OneToManyPluralAttributeElementSource {
	private final PluralAttributeElement pluralAttributeElement;
	private final JaxbOneToManyElement oneToManyElement;
	private final LocalBindingContext bindingContext;

	public OneToManyPluralAttributeElementSourceImpl(
			PluralAttributeElement pluralAttributeElement,
			JaxbOneToManyElement oneToManyElement,
			LocalBindingContext bindingContext) {
		this.pluralAttributeElement = pluralAttributeElement;
		this.oneToManyElement = oneToManyElement;
		this.bindingContext = bindingContext;
	}

	@Override
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.ONE_TO_MANY;
	}

	@Override
	public String getReferencedEntityName() {
		return StringHelper.isNotEmpty( oneToManyElement.getEntityName() )
				? oneToManyElement.getEntityName()
				: bindingContext.qualifyClassName( oneToManyElement.getClazz() );
	}

	@Override
	public boolean isNotFoundAnException() {
		return oneToManyElement.getNotFound() == null
				|| ! "ignore".equals( oneToManyElement.getNotFound().value() );
	}

	@Override
	public Iterable<CascadeStyle> getCascadeStyles() {
		return Helper.interpretCascadeStyles( pluralAttributeElement.getCascade(), bindingContext );
	}
}
