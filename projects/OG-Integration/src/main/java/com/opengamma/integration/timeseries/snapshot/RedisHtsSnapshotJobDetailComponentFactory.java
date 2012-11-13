/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.integration.timeseries.snapshot;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.JobDetailBean;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesMaster;
import com.opengamma.util.redis.RedisConnector;

/**
 * Component factory to setup a Redis Hts snapshotter job detail for quartz scheduler
 */
@BeanDefinition
public class RedisHtsSnapshotJobDetailComponentFactory extends AbstractComponentFactory {
  
  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  
  @PropertyDefinition(validate = "notNull")
  private String _name;
  
  @PropertyDefinition(validate = "notNull")
  private String _baseDir;
  
  @PropertyDefinition
  private String _group;
  
  @PropertyDefinition(validate = "notNull")
  private Scheduler _scheduler;
    
  @PropertyDefinition(validate = "notNull")
  private String _dataSource;
  
  @PropertyDefinition(validate = "notNull")
  private String _normalizationRuleSetId;
    
  @PropertyDefinition(validate = "notNull")
  private String _globalPrefix;
  
  @PropertyDefinition(validate = "notNull")
  private HistoricalTimeSeriesMaster _htsMaster;
  
  @PropertyDefinition(validate = "notNull")
  private RedisConnector _redisConnector;
  
  @PropertyDefinition
  private String _schemeBlackList;
  
  @PropertyDefinition
  private String _dataFieldBlackList;
  
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    
    ComponentInfo info = new ComponentInfo(JobDetail.class, getClassifier());
    
    JobDetailBean jobDetailBean = new JobDetailBean();
    jobDetailBean.setBeanName(getName());
    jobDetailBean.setJobClass(QuartzRedisHtsSnapshotJob.class);
    if (getGroup() != null) {
      jobDetailBean.setGroup(getGroup());
    }    
    final JobDataMap jobDataMap = jobDetailBean.getJobDataMap();
    jobDataMap.put("dataSource", getDataSource());
    jobDataMap.put("normalizationRuleSetId", getNormalizationRuleSetId());
    if (getDataFieldBlackList() != null) {
      jobDataMap.put("dataFieldBlackList", createBlackList(getDataFieldBlackList(), "RedisDataFieldBlackList"));
    }
    if (getSchemeBlackList() != null) {
      jobDataMap.put("schemeBlackList", createBlackList(getSchemeBlackList(), "RedisSchemeBlackList"));
    }
    jobDataMap.put("globalPrefix", getGlobalPrefix());
    jobDataMap.put("htsMaster", getHtsMaster());
    jobDataMap.put("redisConnector", getRedisConnector());
    jobDataMap.put("baseDir", getBaseDir());
    
    jobDetailBean.afterPropertiesSet();
        
    Scheduler scheduler = getScheduler();
    scheduler.addJob(jobDetailBean, true);
    
    repo.registerComponent(info, jobDetailBean);
    
  }
  
  private BlackList createBlackList(String blackList, String name) {
    DefaultBlackList result = new DefaultBlackList();
    result.setName(name);
    result.setBlackList(Arrays.asList(StringUtils.split(blackList.toUpperCase())));
    return result;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code RedisHtsSnapshotJobDetailComponentFactory}.
   * @return the meta-bean, not null
   */
  public static RedisHtsSnapshotJobDetailComponentFactory.Meta meta() {
    return RedisHtsSnapshotJobDetailComponentFactory.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(RedisHtsSnapshotJobDetailComponentFactory.Meta.INSTANCE);
  }

  @Override
  public RedisHtsSnapshotJobDetailComponentFactory.Meta metaBean() {
    return RedisHtsSnapshotJobDetailComponentFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        return getClassifier();
      case 3373707:  // name
        return getName();
      case -332642308:  // baseDir
        return getBaseDir();
      case 98629247:  // group
        return getGroup();
      case -160710469:  // scheduler
        return getScheduler();
      case 1272470629:  // dataSource
        return getDataSource();
      case 650692196:  // normalizationRuleSetId
        return getNormalizationRuleSetId();
      case -747889643:  // globalPrefix
        return getGlobalPrefix();
      case -1707859415:  // htsMaster
        return getHtsMaster();
      case -745461486:  // redisConnector
        return getRedisConnector();
      case 836243736:  // schemeBlackList
        return getSchemeBlackList();
      case 1058119597:  // dataFieldBlackList
        return getDataFieldBlackList();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        setClassifier((String) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
      case -332642308:  // baseDir
        setBaseDir((String) newValue);
        return;
      case 98629247:  // group
        setGroup((String) newValue);
        return;
      case -160710469:  // scheduler
        setScheduler((Scheduler) newValue);
        return;
      case 1272470629:  // dataSource
        setDataSource((String) newValue);
        return;
      case 650692196:  // normalizationRuleSetId
        setNormalizationRuleSetId((String) newValue);
        return;
      case -747889643:  // globalPrefix
        setGlobalPrefix((String) newValue);
        return;
      case -1707859415:  // htsMaster
        setHtsMaster((HistoricalTimeSeriesMaster) newValue);
        return;
      case -745461486:  // redisConnector
        setRedisConnector((RedisConnector) newValue);
        return;
      case 836243736:  // schemeBlackList
        setSchemeBlackList((String) newValue);
        return;
      case 1058119597:  // dataFieldBlackList
        setDataFieldBlackList((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_classifier, "classifier");
    JodaBeanUtils.notNull(_name, "name");
    JodaBeanUtils.notNull(_baseDir, "baseDir");
    JodaBeanUtils.notNull(_scheduler, "scheduler");
    JodaBeanUtils.notNull(_dataSource, "dataSource");
    JodaBeanUtils.notNull(_normalizationRuleSetId, "normalizationRuleSetId");
    JodaBeanUtils.notNull(_globalPrefix, "globalPrefix");
    JodaBeanUtils.notNull(_htsMaster, "htsMaster");
    JodaBeanUtils.notNull(_redisConnector, "redisConnector");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      RedisHtsSnapshotJobDetailComponentFactory other = (RedisHtsSnapshotJobDetailComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getBaseDir(), other.getBaseDir()) &&
          JodaBeanUtils.equal(getGroup(), other.getGroup()) &&
          JodaBeanUtils.equal(getScheduler(), other.getScheduler()) &&
          JodaBeanUtils.equal(getDataSource(), other.getDataSource()) &&
          JodaBeanUtils.equal(getNormalizationRuleSetId(), other.getNormalizationRuleSetId()) &&
          JodaBeanUtils.equal(getGlobalPrefix(), other.getGlobalPrefix()) &&
          JodaBeanUtils.equal(getHtsMaster(), other.getHtsMaster()) &&
          JodaBeanUtils.equal(getRedisConnector(), other.getRedisConnector()) &&
          JodaBeanUtils.equal(getSchemeBlackList(), other.getSchemeBlackList()) &&
          JodaBeanUtils.equal(getDataFieldBlackList(), other.getDataFieldBlackList()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getBaseDir());
    hash += hash * 31 + JodaBeanUtils.hashCode(getGroup());
    hash += hash * 31 + JodaBeanUtils.hashCode(getScheduler());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDataSource());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNormalizationRuleSetId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getGlobalPrefix());
    hash += hash * 31 + JodaBeanUtils.hashCode(getHtsMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRedisConnector());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSchemeBlackList());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDataFieldBlackList());
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
   * Gets the name.
   * @return the value of the property, not null
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name.
   * @param name  the new value of the property, not null
   */
  public void setName(String name) {
    JodaBeanUtils.notNull(name, "name");
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
   * Gets the baseDir.
   * @return the value of the property, not null
   */
  public String getBaseDir() {
    return _baseDir;
  }

  /**
   * Sets the baseDir.
   * @param baseDir  the new value of the property, not null
   */
  public void setBaseDir(String baseDir) {
    JodaBeanUtils.notNull(baseDir, "baseDir");
    this._baseDir = baseDir;
  }

  /**
   * Gets the the {@code baseDir} property.
   * @return the property, not null
   */
  public final Property<String> baseDir() {
    return metaBean().baseDir().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the group.
   * @return the value of the property
   */
  public String getGroup() {
    return _group;
  }

  /**
   * Sets the group.
   * @param group  the new value of the property
   */
  public void setGroup(String group) {
    this._group = group;
  }

  /**
   * Gets the the {@code group} property.
   * @return the property, not null
   */
  public final Property<String> group() {
    return metaBean().group().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the scheduler.
   * @return the value of the property, not null
   */
  public Scheduler getScheduler() {
    return _scheduler;
  }

  /**
   * Sets the scheduler.
   * @param scheduler  the new value of the property, not null
   */
  public void setScheduler(Scheduler scheduler) {
    JodaBeanUtils.notNull(scheduler, "scheduler");
    this._scheduler = scheduler;
  }

  /**
   * Gets the the {@code scheduler} property.
   * @return the property, not null
   */
  public final Property<Scheduler> scheduler() {
    return metaBean().scheduler().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the dataSource.
   * @return the value of the property, not null
   */
  public String getDataSource() {
    return _dataSource;
  }

  /**
   * Sets the dataSource.
   * @param dataSource  the new value of the property, not null
   */
  public void setDataSource(String dataSource) {
    JodaBeanUtils.notNull(dataSource, "dataSource");
    this._dataSource = dataSource;
  }

  /**
   * Gets the the {@code dataSource} property.
   * @return the property, not null
   */
  public final Property<String> dataSource() {
    return metaBean().dataSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the normalizationRuleSetId.
   * @return the value of the property, not null
   */
  public String getNormalizationRuleSetId() {
    return _normalizationRuleSetId;
  }

  /**
   * Sets the normalizationRuleSetId.
   * @param normalizationRuleSetId  the new value of the property, not null
   */
  public void setNormalizationRuleSetId(String normalizationRuleSetId) {
    JodaBeanUtils.notNull(normalizationRuleSetId, "normalizationRuleSetId");
    this._normalizationRuleSetId = normalizationRuleSetId;
  }

  /**
   * Gets the the {@code normalizationRuleSetId} property.
   * @return the property, not null
   */
  public final Property<String> normalizationRuleSetId() {
    return metaBean().normalizationRuleSetId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the globalPrefix.
   * @return the value of the property, not null
   */
  public String getGlobalPrefix() {
    return _globalPrefix;
  }

  /**
   * Sets the globalPrefix.
   * @param globalPrefix  the new value of the property, not null
   */
  public void setGlobalPrefix(String globalPrefix) {
    JodaBeanUtils.notNull(globalPrefix, "globalPrefix");
    this._globalPrefix = globalPrefix;
  }

  /**
   * Gets the the {@code globalPrefix} property.
   * @return the property, not null
   */
  public final Property<String> globalPrefix() {
    return metaBean().globalPrefix().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the htsMaster.
   * @return the value of the property, not null
   */
  public HistoricalTimeSeriesMaster getHtsMaster() {
    return _htsMaster;
  }

  /**
   * Sets the htsMaster.
   * @param htsMaster  the new value of the property, not null
   */
  public void setHtsMaster(HistoricalTimeSeriesMaster htsMaster) {
    JodaBeanUtils.notNull(htsMaster, "htsMaster");
    this._htsMaster = htsMaster;
  }

  /**
   * Gets the the {@code htsMaster} property.
   * @return the property, not null
   */
  public final Property<HistoricalTimeSeriesMaster> htsMaster() {
    return metaBean().htsMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the redisConnector.
   * @return the value of the property, not null
   */
  public RedisConnector getRedisConnector() {
    return _redisConnector;
  }

  /**
   * Sets the redisConnector.
   * @param redisConnector  the new value of the property, not null
   */
  public void setRedisConnector(RedisConnector redisConnector) {
    JodaBeanUtils.notNull(redisConnector, "redisConnector");
    this._redisConnector = redisConnector;
  }

  /**
   * Gets the the {@code redisConnector} property.
   * @return the property, not null
   */
  public final Property<RedisConnector> redisConnector() {
    return metaBean().redisConnector().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the schemeBlackList.
   * @return the value of the property
   */
  public String getSchemeBlackList() {
    return _schemeBlackList;
  }

  /**
   * Sets the schemeBlackList.
   * @param schemeBlackList  the new value of the property
   */
  public void setSchemeBlackList(String schemeBlackList) {
    this._schemeBlackList = schemeBlackList;
  }

  /**
   * Gets the the {@code schemeBlackList} property.
   * @return the property, not null
   */
  public final Property<String> schemeBlackList() {
    return metaBean().schemeBlackList().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the dataFieldBlackList.
   * @return the value of the property
   */
  public String getDataFieldBlackList() {
    return _dataFieldBlackList;
  }

  /**
   * Sets the dataFieldBlackList.
   * @param dataFieldBlackList  the new value of the property
   */
  public void setDataFieldBlackList(String dataFieldBlackList) {
    this._dataFieldBlackList = dataFieldBlackList;
  }

  /**
   * Gets the the {@code dataFieldBlackList} property.
   * @return the property, not null
   */
  public final Property<String> dataFieldBlackList() {
    return metaBean().dataFieldBlackList().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code RedisHtsSnapshotJobDetailComponentFactory}.
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
        this, "classifier", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code baseDir} property.
     */
    private final MetaProperty<String> _baseDir = DirectMetaProperty.ofReadWrite(
        this, "baseDir", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code group} property.
     */
    private final MetaProperty<String> _group = DirectMetaProperty.ofReadWrite(
        this, "group", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code scheduler} property.
     */
    private final MetaProperty<Scheduler> _scheduler = DirectMetaProperty.ofReadWrite(
        this, "scheduler", RedisHtsSnapshotJobDetailComponentFactory.class, Scheduler.class);
    /**
     * The meta-property for the {@code dataSource} property.
     */
    private final MetaProperty<String> _dataSource = DirectMetaProperty.ofReadWrite(
        this, "dataSource", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code normalizationRuleSetId} property.
     */
    private final MetaProperty<String> _normalizationRuleSetId = DirectMetaProperty.ofReadWrite(
        this, "normalizationRuleSetId", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code globalPrefix} property.
     */
    private final MetaProperty<String> _globalPrefix = DirectMetaProperty.ofReadWrite(
        this, "globalPrefix", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code htsMaster} property.
     */
    private final MetaProperty<HistoricalTimeSeriesMaster> _htsMaster = DirectMetaProperty.ofReadWrite(
        this, "htsMaster", RedisHtsSnapshotJobDetailComponentFactory.class, HistoricalTimeSeriesMaster.class);
    /**
     * The meta-property for the {@code redisConnector} property.
     */
    private final MetaProperty<RedisConnector> _redisConnector = DirectMetaProperty.ofReadWrite(
        this, "redisConnector", RedisHtsSnapshotJobDetailComponentFactory.class, RedisConnector.class);
    /**
     * The meta-property for the {@code schemeBlackList} property.
     */
    private final MetaProperty<String> _schemeBlackList = DirectMetaProperty.ofReadWrite(
        this, "schemeBlackList", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code dataFieldBlackList} property.
     */
    private final MetaProperty<String> _dataFieldBlackList = DirectMetaProperty.ofReadWrite(
        this, "dataFieldBlackList", RedisHtsSnapshotJobDetailComponentFactory.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "name",
        "baseDir",
        "group",
        "scheduler",
        "dataSource",
        "normalizationRuleSetId",
        "globalPrefix",
        "htsMaster",
        "redisConnector",
        "schemeBlackList",
        "dataFieldBlackList");

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
        case 3373707:  // name
          return _name;
        case -332642308:  // baseDir
          return _baseDir;
        case 98629247:  // group
          return _group;
        case -160710469:  // scheduler
          return _scheduler;
        case 1272470629:  // dataSource
          return _dataSource;
        case 650692196:  // normalizationRuleSetId
          return _normalizationRuleSetId;
        case -747889643:  // globalPrefix
          return _globalPrefix;
        case -1707859415:  // htsMaster
          return _htsMaster;
        case -745461486:  // redisConnector
          return _redisConnector;
        case 836243736:  // schemeBlackList
          return _schemeBlackList;
        case 1058119597:  // dataFieldBlackList
          return _dataFieldBlackList;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends RedisHtsSnapshotJobDetailComponentFactory> builder() {
      return new DirectBeanBuilder<RedisHtsSnapshotJobDetailComponentFactory>(new RedisHtsSnapshotJobDetailComponentFactory());
    }

    @Override
    public Class<? extends RedisHtsSnapshotJobDetailComponentFactory> beanType() {
      return RedisHtsSnapshotJobDetailComponentFactory.class;
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
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code baseDir} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> baseDir() {
      return _baseDir;
    }

    /**
     * The meta-property for the {@code group} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> group() {
      return _group;
    }

    /**
     * The meta-property for the {@code scheduler} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Scheduler> scheduler() {
      return _scheduler;
    }

    /**
     * The meta-property for the {@code dataSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dataSource() {
      return _dataSource;
    }

    /**
     * The meta-property for the {@code normalizationRuleSetId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> normalizationRuleSetId() {
      return _normalizationRuleSetId;
    }

    /**
     * The meta-property for the {@code globalPrefix} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> globalPrefix() {
      return _globalPrefix;
    }

    /**
     * The meta-property for the {@code htsMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<HistoricalTimeSeriesMaster> htsMaster() {
      return _htsMaster;
    }

    /**
     * The meta-property for the {@code redisConnector} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<RedisConnector> redisConnector() {
      return _redisConnector;
    }

    /**
     * The meta-property for the {@code schemeBlackList} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> schemeBlackList() {
      return _schemeBlackList;
    }

    /**
     * The meta-property for the {@code dataFieldBlackList} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dataFieldBlackList() {
      return _dataFieldBlackList;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
