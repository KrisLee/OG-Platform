/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.master;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.time.Instant;

import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicMetaBean;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaProperty;

import com.opengamma.engine.security.DefaultSecurity;
import com.opengamma.id.UniqueIdentifier;
import com.opengamma.util.ArgumentChecker;

/**
 * A document used to pass into and out of the security master.
 */
@BeanDefinition
public class SecurityDocument extends DirectBean {

  /**
   * The security unique identifier.
   */
  @PropertyDefinition
  private UniqueIdentifier _securityId;
  /**
   * The start of an interval that the version of the security is accurate for.
   */
  @PropertyDefinition
  private Instant _versionFromInstant;
  /**
   * The end of an interval that the version of the security is accurate for.
   * Null indicates this is the latest version.
   */
  @PropertyDefinition
  private Instant _versionToInstant;
  /**
   * The start of an interval that the correction of the version of the security is accurate for.
   */
  @PropertyDefinition
  private Instant _correctionFromInstant;
  /**
   * The end of an interval that the correction of the version of the security is accurate for.
   * Null indicates this is the latest correction.
   */
  @PropertyDefinition
  private Instant _correctionToInstant;
  /**
   * The security.
   */
  @PropertyDefinition
  private DefaultSecurity _security;

  /**
   * Creates an instance.
   */
  public SecurityDocument() {
  }

  /**
   * Creates an instance from a security.
   * @param security  the security, not null
   */
  public SecurityDocument(final DefaultSecurity security) {
    ArgumentChecker.notNull(security, "security");
    setSecurityId(security.getUniqueIdentifier());
    setSecurity(security);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SecurityDocument}.
   * @return the meta-bean, not null
   */
  public static SecurityDocument.Meta meta() {
    return SecurityDocument.Meta.INSTANCE;
  }

  @Override
  public SecurityDocument.Meta metaBean() {
    return SecurityDocument.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case 1574023291:  // securityId
        return getSecurityId();
      case 2006263519:  // versionFromInstant
        return getVersionFromInstant();
      case 1577022702:  // versionToInstant
        return getVersionToInstant();
      case 1808757913:  // correctionFromInstant
        return getCorrectionFromInstant();
      case 973465896:  // correctionToInstant
        return getCorrectionToInstant();
      case 949122880:  // security
        return getSecurity();
    }
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case 1574023291:  // securityId
        setSecurityId((UniqueIdentifier) newValue);
        return;
      case 2006263519:  // versionFromInstant
        setVersionFromInstant((Instant) newValue);
        return;
      case 1577022702:  // versionToInstant
        setVersionToInstant((Instant) newValue);
        return;
      case 1808757913:  // correctionFromInstant
        setCorrectionFromInstant((Instant) newValue);
        return;
      case 973465896:  // correctionToInstant
        setCorrectionToInstant((Instant) newValue);
        return;
      case 949122880:  // security
        setSecurity((DefaultSecurity) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the security unique identifier.
   * @return the value of the property
   */
  public UniqueIdentifier getSecurityId() {
    return _securityId;
  }

  /**
   * Sets the security unique identifier.
   * @param securityId  the new value of the property
   */
  public void setSecurityId(UniqueIdentifier securityId) {
    this._securityId = securityId;
  }

  /**
   * Gets the the {@code securityId} property.
   * @return the property, not null
   */
  public final Property<UniqueIdentifier> securityId() {
    return metaBean().securityId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start of an interval that the version of the security is accurate for.
   * @return the value of the property
   */
  public Instant getVersionFromInstant() {
    return _versionFromInstant;
  }

  /**
   * Sets the start of an interval that the version of the security is accurate for.
   * @param versionFromInstant  the new value of the property
   */
  public void setVersionFromInstant(Instant versionFromInstant) {
    this._versionFromInstant = versionFromInstant;
  }

  /**
   * Gets the the {@code versionFromInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> versionFromInstant() {
    return metaBean().versionFromInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the end of an interval that the version of the security is accurate for.
   * Null indicates this is the latest version.
   * @return the value of the property
   */
  public Instant getVersionToInstant() {
    return _versionToInstant;
  }

  /**
   * Sets the end of an interval that the version of the security is accurate for.
   * Null indicates this is the latest version.
   * @param versionToInstant  the new value of the property
   */
  public void setVersionToInstant(Instant versionToInstant) {
    this._versionToInstant = versionToInstant;
  }

  /**
   * Gets the the {@code versionToInstant} property.
   * Null indicates this is the latest version.
   * @return the property, not null
   */
  public final Property<Instant> versionToInstant() {
    return metaBean().versionToInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start of an interval that the correction of the version of the security is accurate for.
   * @return the value of the property
   */
  public Instant getCorrectionFromInstant() {
    return _correctionFromInstant;
  }

  /**
   * Sets the start of an interval that the correction of the version of the security is accurate for.
   * @param correctionFromInstant  the new value of the property
   */
  public void setCorrectionFromInstant(Instant correctionFromInstant) {
    this._correctionFromInstant = correctionFromInstant;
  }

  /**
   * Gets the the {@code correctionFromInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> correctionFromInstant() {
    return metaBean().correctionFromInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the end of an interval that the correction of the version of the security is accurate for.
   * Null indicates this is the latest correction.
   * @return the value of the property
   */
  public Instant getCorrectionToInstant() {
    return _correctionToInstant;
  }

  /**
   * Sets the end of an interval that the correction of the version of the security is accurate for.
   * Null indicates this is the latest correction.
   * @param correctionToInstant  the new value of the property
   */
  public void setCorrectionToInstant(Instant correctionToInstant) {
    this._correctionToInstant = correctionToInstant;
  }

  /**
   * Gets the the {@code correctionToInstant} property.
   * Null indicates this is the latest correction.
   * @return the property, not null
   */
  public final Property<Instant> correctionToInstant() {
    return metaBean().correctionToInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the security.
   * @return the value of the property
   */
  public DefaultSecurity getSecurity() {
    return _security;
  }

  /**
   * Sets the security.
   * @param security  the new value of the property
   */
  public void setSecurity(DefaultSecurity security) {
    this._security = security;
  }

  /**
   * Gets the the {@code security} property.
   * @return the property, not null
   */
  public final Property<DefaultSecurity> security() {
    return metaBean().security().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SecurityDocument}.
   */
  public static class Meta extends BasicMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code securityId} property.
     */
    private final MetaProperty<UniqueIdentifier> _securityId = DirectMetaProperty.ofReadWrite(this, "securityId", UniqueIdentifier.class);
    /**
     * The meta-property for the {@code versionFromInstant} property.
     */
    private final MetaProperty<Instant> _versionFromInstant = DirectMetaProperty.ofReadWrite(this, "versionFromInstant", Instant.class);
    /**
     * The meta-property for the {@code versionToInstant} property.
     */
    private final MetaProperty<Instant> _versionToInstant = DirectMetaProperty.ofReadWrite(this, "versionToInstant", Instant.class);
    /**
     * The meta-property for the {@code correctionFromInstant} property.
     */
    private final MetaProperty<Instant> _correctionFromInstant = DirectMetaProperty.ofReadWrite(this, "correctionFromInstant", Instant.class);
    /**
     * The meta-property for the {@code correctionToInstant} property.
     */
    private final MetaProperty<Instant> _correctionToInstant = DirectMetaProperty.ofReadWrite(this, "correctionToInstant", Instant.class);
    /**
     * The meta-property for the {@code security} property.
     */
    private final MetaProperty<DefaultSecurity> _security = DirectMetaProperty.ofReadWrite(this, "security", DefaultSecurity.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings("unchecked")
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap();
      temp.put("securityId", _securityId);
      temp.put("versionFromInstant", _versionFromInstant);
      temp.put("versionToInstant", _versionToInstant);
      temp.put("correctionFromInstant", _correctionFromInstant);
      temp.put("correctionToInstant", _correctionToInstant);
      temp.put("security", _security);
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public SecurityDocument createBean() {
      return new SecurityDocument();
    }

    @Override
    public Class<? extends SecurityDocument> beanType() {
      return SecurityDocument.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code securityId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueIdentifier> securityId() {
      return _securityId;
    }

    /**
     * The meta-property for the {@code versionFromInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> versionFromInstant() {
      return _versionFromInstant;
    }

    /**
     * The meta-property for the {@code versionToInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> versionToInstant() {
      return _versionToInstant;
    }

    /**
     * The meta-property for the {@code correctionFromInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> correctionFromInstant() {
      return _correctionFromInstant;
    }

    /**
     * The meta-property for the {@code correctionToInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> correctionToInstant() {
      return _correctionToInstant;
    }

    /**
     * The meta-property for the {@code security} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DefaultSecurity> security() {
      return _security;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
