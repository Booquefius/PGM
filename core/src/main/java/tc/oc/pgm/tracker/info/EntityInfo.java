package tc.oc.pgm.tracker.info;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import tc.oc.pgm.api.player.ParticipantState;
import tc.oc.pgm.api.tracker.info.PhysicalInfo;
import tc.oc.util.bukkit.component.Component;
import tc.oc.util.bukkit.component.Components;
import tc.oc.util.bukkit.component.types.PersonalizedText;
import tc.oc.util.bukkit.component.types.PersonalizedTranslatable;
import tc.oc.util.bukkit.nms.NMSHacks;

public class EntityInfo extends OwnerInfoBase implements PhysicalInfo {

  private final EntityType entityType;
  private final @Nullable String customName;

  public EntityInfo(
      EntityType entityType, @Nullable String customName, @Nullable ParticipantState owner) {
    super(owner);
    this.entityType = checkNotNull(entityType);
    this.customName = customName;
  }

  public EntityInfo(EntityType entityType, @Nullable String customName) {
    this(entityType, customName, null);
  }

  public EntityInfo(Entity entity, @Nullable ParticipantState owner) {
    this(entity.getType(), entity.getCustomName(), owner);
  }

  public EntityInfo(Entity entity) {
    this(entity, null);
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public @Nullable String getCustomName() {
    return customName;
  }

  @Override
  public String getIdentifier() {
    return getEntityType().getName();
  }

  @Override
  public Component getLocalizedName() {
    if (getCustomName() != null) {
      return Components.fromLegacyText(getCustomName());
    } else {
      String key;
      switch (getEntityType()) {
        case PRIMED_TNT:
          key = "tile.tnt.name";
          break;

        default:
          key = NMSHacks.getTranslationKey(getEntityType());
          break;
      }
      return key != null
          ? new PersonalizedTranslatable(key)
          : new PersonalizedText(getEntityType().getName());
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "{entity="
        + getEntityType()
        + " name="
        + getCustomName()
        + " owner="
        + getOwner()
        + "}";
  }
}
