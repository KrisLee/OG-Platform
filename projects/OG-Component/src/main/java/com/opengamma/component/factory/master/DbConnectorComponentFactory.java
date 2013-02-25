/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.component.factory.master;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.util.db.DbConnector;
import com.opengamma.util.db.DbDialect;

/**
 * Component factory for a database connector.
 */
@BeanDefinition
public class DbConnectorComponentFactory extends AbstractComponentFactory {

  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  /**
   * The data source.
   */
  @PropertyDefinition(validate = "notNull")
  private DataSource _dataSource;
  /**
   * The database dialect helper class.
   */
  @PropertyDefinition(validate = "notNull")
  private String _dialect;
  /**
   * The transaction isolation level.
   */
  @PropertyDefinition
  private String _transactionIsolationLevel;
  /**
   * The transaction propagation behavior.
   */
  @PropertyDefinition
  private String _transactionPropagationBehavior;
  /**
   * The transaction timeout in seconds.
   */
  @PropertyDefinition
  private int _transactionTimeout;
  /**
   * The name of the data to be accessed, defaults to the classifier. 
   */
  @PropertyDefinition
  private String _name;
  
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    if (getName() == null) {
      setName(getClassifier());
    }
    initDbConnector(repo);
  }
  
  protected DbConnector initDbConnector(ComponentRepository repo) {
    DbDialect dialect = createDialect();
    SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(getDataSource());
    TransactionTemplate transactionTemplate = createTransactionTemplate();
    DbConnector dbConnector = new DbConnector(getName(), dialect, getDataSource(), jdbcTemplate, null, transactionTemplate);
    
    ComponentInfo info = new ComponentInfo(DbConnector.class, getClassifier());
    repo.registerComponent(info, dbConnector);
    return dbConnector;
  }

  private TransactionTemplate createTransactionTemplate() {
    DefaultTransactionDefinition transactionDef = new DefaultTransactionDefinition();
    transactionDef.setName(getName());
    if (getTransactionIsolationLevel() != null) {
      transactionDef.setIsolationLevelName(getTransactionIsolationLevel());
    }
    if (getTransactionPropagationBehavior() != null) {
      transactionDef.setPropagationBehaviorName(getTransactionPropagationBehavior());
    }
    if (getTransactionTimeout() != 0) {
      transactionDef.setTimeout(getTransactionTimeout());
    }
    return new TransactionTemplate(new DataSourceTransactionManager(getDataSource()), transactionDef);
  }
  
  //-------------------------------------------------------------------------
  /**
   * Creates the database dialect.
   * 
   * @return the dialect, not null
   */
  protected DbDialect createDialect() {
    try {
      return (DbDialect) getClass().getClassLoader().loadClass(getDialect()).newInstance();
    } catch (Exception e) {
      throw new OpenGammaRuntimeException("Unable to create database dialect from class '" + getDialect() + "'", e);
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DbConnectorComponentFactory}.
   * @return the meta-bean, not null
   */
  public static DbConnectorComponentFactory.Meta meta() {
    return DbConnectorComponentFactory.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(DbConnectorComponentFactory.Meta.INSTANCE);
  }

  @Override
  public DbConnectorComponentFactory.Meta metaBean() {
    return DbConnectorComponentFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        return getClassifier();
      case 1272470629:  // dataSource
        return getDataSource();
      case 1655014950:  // dialect
        return getDialect();
      case 1321533396:  // transactionIsolationLevel
        return getTransactionIsolationLevel();
      case 230249600:  // transactionPropagationBehavior
        return getTransactionPropagationBehavior();
      case -1923367773:  // transactionTimeout
        return getTransactionTimeout();
      case 3373707:  // name
        return getName();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        setClassifier((String) newValue);
        return;
      case 1272470629:  // dataSource
        setDataSource((DataSource) newValue);
        return;
      case 1655014950:  // dialect
        setDialect((String) newValue);
        return;
      case 1321533396:  // transactionIsolationLevel
        setTransactionIsolationLevel((String) newValue);
        return;
      case 230249600:  // transactionPropagationBehavior
        setTransactionPropagationBehavior((String) newValue);
        return;
      case -1923367773:  // transactionTimeout
        setTransactionTimeout((Integer) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_classifier, "classifier");
    JodaBeanUtils.notNull(_dataSource, "dataSource");
    JodaBeanUtils.notNull(_dialect, "dialect");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DbConnectorComponentFactory other = (DbConnectorComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(getDataSource(), other.getDataSource()) &&
          JodaBeanUtils.equal(getDialect(), other.getDialect()) &&
          JodaBeanUtils.equal(getTransactionIsolationLevel(), other.getTransactionIsolationLevel()) &&
          JodaBeanUtils.equal(getTransactionPropagationBehavior(), other.getTransactionPropagationBehavior()) &&
          JodaBeanUtils.equal(getTransactionTimeout(), other.getTransactionTimeout()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDataSource());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDialect());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTransactionIsolationLevel());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTransactionPropagationBehavior());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTransactionTimeout());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the data source.
   * @return the value of the property, not null
   */
  public DataSource getDataSource() {
    return _dataSource;
  }

  /**
   * Sets the data source.
   * @param dataSource  the new value of the property, not null
   */
  public void setDataSource(DataSource dataSource) {
    JodaBeanUtils.notNull(dataSource, "dataSource");
    this._dataSource = dataSource;
  }

  /**
   * Gets the the {@code dataSource} property.
   * @return the property, not null
   */
  public final Property<DataSource> dataSource() {
    return metaBean().dataSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the database dialect helper class.
   * @return the value of the property, not null
   */
  public String getDialect() {
    return _dialect;
  }

  /**
   * Sets the database dialect helper class.
   * @param dialect  the new value of the property, not null
   */
  public void setDialect(String dialect) {
    JodaBeanUtils.notNull(dialect, "dialect");
    this._dialect = dialect;
  }

  /**
   * Gets the the {@code dialect} property.
   * @return the property, not null
   */
  public final Property<String> dialect() {
    return metaBean().dialect().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the transaction isolation level.
   * @return the value of the property
   */
  public String getTransactionIsolationLevel() {
    return _transactionIsolationLevel;
  }

  /**
   * Sets the transaction isolation level.
   * @param transactionIsolationLevel  the new value of the property
   */
  public void setTransactionIsolationLevel(String transactionIsolationLevel) {
    this._transactionIsolationLevel = transactionIsolationLevel;
  }

  /**
   * Gets the the {@code transactionIsolationLevel} property.
   * @return the property, not null
   */
  public final Property<String> transactionIsolationLevel() {
    return metaBean().transactionIsolationLevel().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the transaction propagation behavior.
   * @return the value of the property
   */
  public String getTransactionPropagationBehavior() {
    return _transactionPropagationBehavior;
  }

  /**
   * Sets the transaction propagation behavior.
   * @param transactionPropagationBehavior  the new value of the property
   */
  public void setTransactionPropagationBehavior(String transactionPropagationBehavior) {
    this._transactionPropagationBehavior = transactionPropagationBehavior;
  }

  /**
   * Gets the the {@code transactionPropagationBehavior} property.
   * @return the property, not null
   */
  public final Property<String> transactionPropagationBehavior() {
    return metaBean().transactionPropagationBehavior().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the transaction timeout in seconds.
   * @return the value of the property
   */
  public int getTransactionTimeout() {
    return _transactionTimeout;
  }

  /**
   * Sets the transaction timeout in seconds.
   * @param transactionTimeout  the new value of the property
   */
  public void setTransactionTimeout(int transactionTimeout) {
    this._transactionTimeout = transactionTimeout;
  }

  /**
   * Gets the the {@code transactionTimeout} property.
   * @return the property, not null
   */
  public final Property<Integer> transactionTimeout() {
    return metaBean().transactionTimeout().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the data to be accessed, defaults to the classifier.
   * @return the value of the property
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name of the data to be accessed, defaults to the classifier.
   * @param name  the new value of the property
   */
  public void setName(String name) {
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DbConnectorComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", DbConnectorComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code dataSource} property.
     */
    private final MetaProperty<DataSource> _dataSource = DirectMetaProperty.ofReadWrite(
        this, "dataSource", DbConnectorComponentFactory.class, DataSource.class);
    /**
     * The meta-property for the {@code dialect} property.
     */
    private final MetaProperty<String> _dialect = DirectMetaProperty.ofReadWrite(
        this, "dialect", DbConnectorComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code transactionIsolationLevel} property.
     */
    private final MetaProperty<String> _transactionIsolationLevel = DirectMetaProperty.ofReadWrite(
        this, "transactionIsolationLevel", DbConnectorComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code transactionPropagationBehavior} property.
     */
    private final MetaProperty<String> _transactionPropagationBehavior = DirectMetaProperty.ofReadWrite(
        this, "transactionPropagationBehavior", DbConnectorComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code transactionTimeout} property.
     */
    private final MetaProperty<Integer> _transactionTimeout = DirectMetaProperty.ofReadWrite(
        this, "transactionTimeout", DbConnectorComponentFactory.class, Integer.TYPE);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", DbConnectorComponentFactory.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "dataSource",
        "dialect",
        "transactionIsolationLevel",
        "transactionPropagationBehavior",
        "transactionTimeout",
        "name");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case 1272470629:  // dataSource
          return _dataSource;
        case 1655014950:  // dialect
          return _dialect;
        case 1321533396:  // transactionIsolationLevel
          return _transactionIsolationLevel;
        case 230249600:  // transactionPropagationBehavior
          return _transactionPropagationBehavior;
        case -1923367773:  // transactionTimeout
          return _transactionTimeout;
        case 3373707:  // name
          return _name;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DbConnectorComponentFactory> builder() {
      return new DirectBeanBuilder<DbConnectorComponentFactory>(new DbConnectorComponentFactory());
    }

    @Override
    public Class<? extends DbConnectorComponentFactory> beanType() {
      return DbConnectorComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code dataSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DataSource> dataSource() {
      return _dataSource;
    }

    /**
     * The meta-property for the {@code dialect} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dialect() {
      return _dialect;
    }

    /**
     * The meta-property for the {@code transactionIsolationLevel} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> transactionIsolationLevel() {
      return _transactionIsolationLevel;
    }

    /**
     * The meta-property for the {@code transactionPropagationBehavior} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> transactionPropagationBehavior() {
      return _transactionPropagationBehavior;
    }

    /**
     * The meta-property for the {@code transactionTimeout} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> transactionTimeout() {
      return _transactionTimeout;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
