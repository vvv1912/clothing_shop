package com.shop.clothing.config.startup;

import com.shop.clothing.auth.Permissions;
import com.shop.clothing.auth.entity.Permission;
import com.shop.clothing.auth.entity.Role;
import com.shop.clothing.auth.repository.PermissionRepository;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.auth.repository.IUserRepository;
import com.shop.clothing.auth.commands.permission.createPermission.CreatePermissionCommand;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component

public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final IUserRepository IUserRepository;


    private final ISender sender;


    private final PasswordEncoder passwordEncoder;
    private final SeedCategory seedCategory;
    private final IUserRepository userRepo;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;


    public SetupDataLoader(IUserRepository IUserRepository, ISender roleService, PasswordEncoder passwordEncoder, SeedCategory seedCategory, IUserRepository userRepo, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.IUserRepository = IUserRepository;
        this.sender = roleService;
        this.passwordEncoder = passwordEncoder;
        this.seedCategory = seedCategory;
        this.userRepo = userRepo;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;

    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        var admin = IUserRepository.findByEmail("admin@admin.com");
//        seedCategory.seedCategory();
//
//        seedRole();
//        seedPermission();
//        if (admin.isPresent()) {
//            alreadySetup = true;
//
//        }
//        if (alreadySetup)
//            return;
//
//
//        createRoleIfNotFound("ROLE_EMPLOYEE", "Nhân viên", "Nhân viên", new ArrayList<>());
//
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
//        User user = new User();
//        user.setFirstName("Admin");
//        user.setLastName("Admin");
//        user.setPasswordHash(passwordEncoder.encode("admin"));
//        user.setEmail("admin@admin.com");
//        user.setRoles(List.of(adminRole));
//        user.setAccountEnabled(true);
//        user.setCustomer(false);
//        IUserRepository.save(user);
//        this.alreadySetup = true;

    }

    @Transactional
    public Permission createPermissionIfNotFound(String name, String displayName, String description) {

        Permission permission = permissionRepository.findByName(name).orElse(null);
        if (permission == null) {
            var result = sender.send(CreatePermissionCommand.builder().normalizedName(name).displayName(displayName).description(description).build());
            permission = permissionRepository.findById(result.get()).orElse(null);
        }

        return permission;
    }

    @Transactional
    public Role createRoleIfNotFound(
            String name, String displayName, String description, List<Permission> permissions) {

        var role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            roleRepository.save(Role.builder().normalizedName(name).displayName(displayName).description(description).build());
        }
        return role;
    }

    @Transactional
    public void seedRole() {

        createRoleIfNotFound("ROLE_ADMIN", "Quản trị viên", "Quản trị viên", new ArrayList<>());
        createRoleIfNotFound("ROLE_SALES_STAFF", "Nhân viên bán hàng", "Vai trò nhân viên bán hàng", new ArrayList<>());
        createRoleIfNotFound("ROLE_STOCK_CLERK", "Nhân viên nhập hàng", "Vai trò nhân viên nhập hàng", new ArrayList<>());
        createRoleIfNotFound("ROLE_CUSTOMER", "Khách hàng", "Vai trò khách hàng", new ArrayList<>());
        createRoleIfNotFound("ROLE_MANAGER", "Quản lý", "Vai trò quản lý", new ArrayList<>());

    }

    @Transactional
    public void seedPermission() {
        createPermissionIfNotFound(Permissions.PRODUCT_MANAGEMENT.toString(), "Quản lý sản phẩm", "Quyền quản lý sản phẩm.");
        createPermissionIfNotFound(Permissions.USER_MANAGEMENT.toString(), "Quản lý người dùng", "Quyền quản lý người dùng.");
        createPermissionIfNotFound(Permissions.ORDER_MANAGEMENT.toString(), "Quản lý đơn hàng", "Quyền quản lý đơn hàng.");
        createPermissionIfNotFound(Permissions.CATEGORY_MANAGEMENT.toString(), "Quản lý danh mục", "Quyền quản lý danh mục.");
        createPermissionIfNotFound(Permissions.PROMOTION_MANAGEMENT.toString(), "Quản lý khuyến mãi", "Quyền quản lý khuyến mãi.");
        createPermissionIfNotFound(Permissions.STOCK_RECEIPT_MANAGEMENT.toString(), "Quản lý nhập hàng", "Quyền quản lý nhập hàng.");
        createPermissionIfNotFound(Permissions.RATING_MANAGEMENT.toString(), "Quản lý đánh giá", "Quyền quản lý đánh giá.");
        createPermissionIfNotFound(Permissions.PAYMENT_MANAGEMENT.toString(), "Quản lý thanh toán", "Quyền quản lý thanh toán.");
        createPermissionIfNotFound(Permissions.SUPPLIER_MANAGEMENT.toString(), "Quản lý nhà cung cấp", "Quyền quản lý nhà cung cấp.");
        createPermissionIfNotFound(Permissions.REPORT_MANAGEMENT.toString(), "Quản lý báo cáo", "Quyền quản lý báo cáo.");
        createPermissionIfNotFound(Permissions.ROLE_MANAGEMENT.toString(), "Quản lý vai trò", "Quyền quản lý vai trò.");
        createPermissionIfNotFound(Permissions.CAN_ORDER.toString(), "Đặt hàng", "Quyền đặt hàng.");
        createPermissionIfNotFound(Permissions.ADMIN_DASHBOARD.toString(), "Trang quản trị", "Quyền truy cập trang quản trị.");
        createPermissionIfNotFound(Permissions.SHOP_INFO_MANAGEMENT.toString(), "Thông tin cửa hàng", "Quyền quản lý thông tin cửa hàng.");

    }
}