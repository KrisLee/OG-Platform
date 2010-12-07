/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.master.db.hibernate;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.core.convention.Frequency;
import com.opengamma.financial.convention.frequency.SimpleFrequencyFactory;

/**
 * Hibernate bean for storing frequency.
 */
public class FrequencyBean extends EnumBean {

  protected FrequencyBean() {
  }

  public FrequencyBean(String frequency) {
    super(frequency);
  }

  /* package */ Frequency toFrequency() {
    final Frequency f = SimpleFrequencyFactory.INSTANCE.getFrequency(getName());
    if (f == null) {
      throw new OpenGammaRuntimeException("Bad value for frequencyBean (" + getName() + ")");
    }
    return f;
  }

}
