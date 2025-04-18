# Folia Porting Guide

## Prerequisites
- [x] [FoliaLib](https://github.com/TechnicallyCoded/FoliaLib) library
- [x] Java 17 or higher
- [ ] Paper/Folia server

## Steps to Port to Folia

### 1. Add FoliaLib Dependency
- [x] Add FoliaLib to your `pom.xml`:
```xml
<dependency>
    <groupId>com.github.technicallycoded</groupId>
    <artifactId>FoliaLib</artifactId>
    <version>main-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

### 2. Update Scheduler Usage
- [ ] Replace all Bukkit scheduler calls with FoliaLib's scheduler
- [ ] Modify `NightPlugin.java`
- [ ] Modify `AbstractDataManager.java`
- [ ] Modify `AbstractUserManager.java`

### 3. Update Task Management
- [ ] Replace all `BukkitTask` and `BukkitScheduler` usage
- [ ] Update task creation methods
- [ ] Modify task cancellation methods

### 4. Update Database Operations
- [ ] Ensure database operations use FoliaLib's async scheduler
- [ ] Update `AbstractDataHandler`
- [ ] Update `AbstractUserDataHandler`
- [ ] Modify data synchronization methods

### 5. Update Event Handling
- [ ] Review and update event handlers in `AbstractListener`
- [ ] Ensure thread safety
- [ ] Register handlers with FoliaLib's scheduler

### 6. Update Command System
- [ ] Review command execution in `CommandManager`
- [ ] Review command execution in `PluginCommand`
- [ ] Ensure proper scheduling

### 7. Update File Operations
- [ ] Review file operations in `AbstractFileData`
- [ ] Review file operations in `FileConfig`
- [ ] Ensure proper async scheduling

### 8. Update User Management
- [ ] Modify `AbstractUserManager`
- [ ] Update user data operations
- [ ] Ensure thread safety

### 9. Update Configuration Management
- [ ] Review configuration loading/saving
- [ ] Ensure proper scheduling
- [ ] Update `FileConfig` operations

### 10. Testing and Validation
- [ ] Test on Folia server
- [ ] Test on non-Folia server
- [ ] Validate all features
- [ ] Check for thread-safety issues

### 11. Documentation Updates
- [ ] Update plugin documentation
- [ ] Document Folia support
- [ ] Note any requirements/limitations

### 12. Version Compatibility
- [ ] Ensure cross-version support
- [ ] Add version checks
- [ ] Implement fallbacks

## Important Notes
- [ ] Always test changes in a development environment first
- [ ] Keep backups of your code
- [ ] Document any breaking changes
- [ ] Consider maintaining separate branches for Folia and non-Folia versions

## Common Issues to Watch For
- [ ] Thread safety violations
- [ ] Improper task scheduling
- [ ] Database connection issues
- [ ] Event handling conflicts

## Resources
- [FoliaLib Documentation](https://github.com/TechnicallyCoded/FoliaLib)
- [PaperMC Documentation](https://docs.papermc.io/)
- [Folia Documentation](https://docs.papermc.io/folia) 