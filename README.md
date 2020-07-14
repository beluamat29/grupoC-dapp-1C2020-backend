[![Build Status](https://travis-ci.com/beluamat29/grupoC-dapp-1C2020-backend.svg?branch=master)](https://travis-ci.com/beluamat29/grupoC-dapp-1C2020-backend)
Release Notes (14/7/2020):
- Features e2e nuevos: 
    - Limite de dinero para los usuarios 
    - Loggin con Facebook 
    - Logger implementado con aspectos 
    - Tests de arquitectura 
    - Carga de productos individual y masiva por CSV
    - Edición de productos de un comercio. Se puede desactivar un producto para que los clientes no lo vean más
    - Edición de los perfiles de los usuarios de los clientes y los comercios
    - Vista de compras realizadas en el perfil del usuario
    - Vaciar un carrito de compras
    - Feature de compra: Eleccion de metodo de pago, y tipo de delivery
    - Envio de mail con confirmación de compra para cliente y confirmacion de pedido para los comercios
    - Barra de busqueda para comercios
    
- Bugs/Issues conocidos: 
  - La página de un comercio no se recarga automaticamente luego de cargar un producto
  - Página de facebook a veces se abre cuando la sesión se cierra
    
FRONTEND:
    - Vista de perfil de usuario
    - Vista de las compras hechas por un usuario
    - Vista de los detalles de un comercio
    - Se agrega el banner de un comercio cuando entras a su lista de productos
    - Vista para realizar compras
    - Login con FB 
    - Busqueda por nombre de comercio
    
WEB LAYER:
    - endpoint para la edicion de usuarios tanto de clientes como de comercios
    - endpoint para agregar y editar productos (incluido CSV)
    - endpoint para realizar compras 
    - endpoint de loggin con Facebook
    - Logger con aspectos que intersecta los endpoints de los controllers
   
BACKEND: 
    - Refactors en el modelo de compras
    - Test de arquitectura
    - Persistencia de las entidades que quedaron pendientes en el sprint anterior
    - Implementacion del modelo de tickets (compra en varios comercios)
