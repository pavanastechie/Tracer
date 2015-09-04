/*
 *  Copyright 2004 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tracer.util;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

import com.tracer.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CommonConverter implements Converter {

	protected Locale locale;
	protected DateFormat formatter;

	/**
	 * Creates new instance of CommonConverter with default locale
	 */
	public CommonConverter() {
		this(Locale.getDefault());
	}

	/**
	 * Creates new instance of Common converter with specified locale
	 *
	 * @param locale Locale to use
	 */
	public CommonConverter(Locale locale) {
		this.locale = locale;
		this.formatter = new SimpleDateFormat(DateUtil.getDatePattern(locale), locale);
	}

    /**
     * Converts an object to specified class (<code>Date</code>, <code>String</code>
     * or <code>Object</code>). In all other cases a <code>ConversionException</code>
     * will be thrown. The only exception is <code>null</code> value: it will
     * give <code>null</code> regardless of <code>type</code>.
     *
     * @param type the class to which the object need to be converted (this can be
     * <code>Date</code>, <code>String</code> or <code>Object</code>)
     * @param value the object that need to be converted
     * @return converted object
     */
	@SuppressWarnings("rawtypes")
	public Object convert(Class type, Object value) {
		if ( value == null ) {
			return null;
		} else if ( type == Date.class ) {
			return convertToDate(type, value);
		} else if ( type == String.class ) {
			return convertToString(type, value);
		} else if ( type == Object.class ) {
			return convertToObject(type, value);
		}

		throw new ConversionException("Could not convert " +
		        value.getClass().getName() + " to " +
		        type.getName());
	}

    /**
     * Converts specified object to <code>Date</code>
     *
     * @param type the class to which the object need to be converted
     * @param value the object that need to be converted
     * @return converted object
     */
	@SuppressWarnings("rawtypes")
	protected Object convertToDate(Class type, Object value) {
		if ( value instanceof Date ) {
			return value;
		}

		if ( value instanceof String ) {
			try {
				if ( StringUtils.isEmpty(value.toString()) ) {
					return null;
				}

				return formatter.parse((String) value);
			} catch ( Exception pe ) {
				throw new ConversionException("Error converting String to Date");
			}
		}

		throw new ConversionException("Could not convert " +
		        value.getClass().getName() + " to " +
		        type.getName());
	}

    /**
     * Converts specified object to <code>String</code>
     *
     * @param type the class to which the object need to be converted
     * @param value the object that need to be converted
     * @return converted object
     */
	@SuppressWarnings("rawtypes")
	protected Object convertToString(Class type, Object value) {
		if ( value instanceof Date ) {
			try {
				return formatter.format(value);
			} catch ( Exception e ) {
				throw new ConversionException("Error converting Date to String");
			}
		}

		return value.toString();
	}

    /**
     * Converts specified object to <code>Object</code>
     *
     * @param type the class to which the object need to be converted
     * @param value the object that need to be converted
     * @return converted object
     */
	@SuppressWarnings("rawtypes")
	protected Object convertToObject(Class type, Object value) {
		return value;
	}

}
