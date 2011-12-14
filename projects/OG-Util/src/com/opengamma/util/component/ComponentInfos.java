/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.Iterables;

/**
 * Information about a principal component of the OpenGamma system.
 */
@BeanDefinition
public class ComponentInfos extends DirectBean {

  /**
   * The component info list.
   */
  @PropertyDefinition(validate = "notNull")
  private final List<ComponentInfo> _infos = new ArrayList<ComponentInfo>();

  /**
   * Creates an instance.
   */
  protected ComponentInfos() {
  }

  /**
   * Creates an instance.
   * 
   * @param infos  the infos to add, not null
   */
  public ComponentInfos(Iterable<ComponentInfo> infos) {
    Iterables.addAll(_infos, infos);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ComponentInfos}.
   * @return the meta-bean, not null
   */
  public static ComponentInfos.Meta meta() {
    return ComponentInfos.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(ComponentInfos.Meta.INSTANCE);
  }

  @Override
  public ComponentInfos.Meta metaBean() {
    return ComponentInfos.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 100348293:  // infos
        return getInfos();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 100348293:  // infos
        setInfos((List<ComponentInfo>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_infos, "infos");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ComponentInfos other = (ComponentInfos) obj;
      return JodaBeanUtils.equal(getInfos(), other.getInfos());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getInfos());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the component info list.
   * @return the value of the property, not null
   */
  public List<ComponentInfo> getInfos() {
    return _infos;
  }

  /**
   * Sets the component info list.
   * @param infos  the new value of the property
   */
  public void setInfos(List<ComponentInfo> infos) {
    this._infos.clear();
    this._infos.addAll(infos);
  }

  /**
   * Gets the the {@code infos} property.
   * @return the property, not null
   */
  public final Property<List<ComponentInfo>> infos() {
    return metaBean().infos().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ComponentInfos}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code infos} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<ComponentInfo>> _infos = DirectMetaProperty.ofReadWrite(
        this, "infos", ComponentInfos.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
        this, null,
        "infos");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100348293:  // infos
          return _infos;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ComponentInfos> builder() {
      return new DirectBeanBuilder<ComponentInfos>(new ComponentInfos());
    }

    @Override
    public Class<? extends ComponentInfos> beanType() {
      return ComponentInfos.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code infos} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<ComponentInfo>> infos() {
      return _infos;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
