
INSERT INTO public.users (id, "updatedAt", deleted_at, username, password, phone, email, "isBlocked")
VALUES (1, null, null, 'buran_manager', '$2a$10$52i24NmLa1HtuMKh.QDsTuFe5u68cbI/CZMP/LMDIRW59XSycCmwK', '89000000000',
        'none', false);

INSERT INTO public.roles(id, "role_name")
VALUES (1, 'ADMIN')

INSERT INTO public.users_roles (user_id, role_id)
VALUES (1, 1)
