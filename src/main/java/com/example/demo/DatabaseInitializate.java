package com.example.demo;

import com.example.demo.model.StoreSchedule;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.Store;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import com.example.demo.repositories.storeSchedule.StoreScheduleRepository;
import com.example.demo.services.StoreService;
import com.example.demo.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializate implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    StoreScheduleRepository storeScheduleRepository;

    @Autowired
    StoreService storeService;

    @Override
    public void run(String... args) throws Exception {
        generateUsers();

        List<StoreCategory> higiene = Arrays.asList(StoreCategory.HYGIENE_PRODUCTS);
        List<StoreCategory> almacen = Arrays.asList(StoreCategory.GROCERY);
        List<StoreCategory> verduleria = Arrays.asList(StoreCategory.GREENGROCES);
        List<StoreCategory> panaderia = Arrays.asList(StoreCategory.BAKERY);
        List<StoreCategory> multiple = Arrays.asList(StoreCategory.BAKERY, StoreCategory.GROCERY);

        Store historietas = generateStore("Calabozo del androide y expendio de tarjetas de baseball", "calle falsa 123", 2, storeScheduleRepository, almacen, "https://s3.us-east-1.amazonaws.com/musiquiatra/upload/monthly_2018_06/800px-Androidsdungeon.png.2d079f7f971d3794cf23275925e32bff.png");
        Store kwickEMart = generateStore("Kwik-E-Mart", "calle apus 123", 3,storeScheduleRepository, multiple, "https://www.tonica.la/__export/1534892802783/sites/debate/img/2018/08/21/kwik-e-mart_sc.jpg_1902800913.jpg");
        Store leftorium = generateStore("Leftorium", "Av. Siempre Viva 123", 2, storeScheduleRepository, higiene, "https://vignette.wikia.nocookie.net/simpsons/images/2/2e/Sex%2C_Pies%2C_and_Idiot_Scrapes_-00144.jpg/revision/latest/scale-to-width-down/340?cb=20150115022043");
        Store cents = generateStore("Tienda de 33 centavos", "calle 2 456", 3, storeScheduleRepository, verduleria, "https://vignette.wikia.nocookie.net/simpsons/images/5/5a/Tumblr_m9wwvdmsDA1r8yo2fo1_500.jpg/revision/latest/top-crop/width/360/height/360?cb=20130124054954");
        Store helados = generateStore("Helados Mantecon", "calle 25 254", 3, storeScheduleRepository, almacen , "https://vignette.wikia.nocookie.net/simpsons/images/c/c4/Splitsville.jpg/revision/latest/top-crop/width/360/height/360?cb=20120105055048");
        Store donas = generateStore("Lard Lad Donuts", "calle 12 587", 3, storeScheduleRepository, almacen , "https://i.pinimg.com/originals/0a/53/2a/0a532a02ec87046356d4f83d097bf896.png");
        Store edna = generateStore("Edna's Edible", "calle 14 1141", 4, storeScheduleRepository, panaderia, "https://vignette.wikia.nocookie.net/simpsons/images/4/40/Edna%27s_Edibles.png/revision/latest?cb=20100519080645");
        Store gorras = generateStore("El Maloso Lanudo", "calle 11 124", 3, storeScheduleRepository, almacen, "https://vignette.wikia.nocookie.net/simpsons/images/9/9c/Wooly_bully.png/revision/latest/top-crop/width/360/height/360?cb=20100903120446");
        Store moe = generateStore("La Taberna de Moe", "calle 2 2524", 3, storeScheduleRepository, almacen, "https://vignette.wikia.nocookie.net/simpsons/images/7/76/Moe%27s_Tavern.PNG/revision/latest?cb=20151025003531");
        Store cleaner = generateStore("Spring Field Cleaner", "calle 4 2524", 3, storeScheduleRepository, higiene, "https://vignette.wikia.nocookie.net/simpsons/images/2/27/Cleaners.jpg/revision/latest/top-crop/width/360/height/360?cb=20120119050327");
        Store burger = generateStore("Krusty Burger", "calle 1 252", 3, storeScheduleRepository, panaderia, "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2020/01/kursty-burger.jpg?itok=bVJGiESu");
        Store monstruomercado = generateStore("Mounstro Mercado", "calle 3 254", 3, storeScheduleRepository, multiple, "https://vignette.wikia.nocookie.net/simpsonstappedout/images/5/56/Monstromart_animation.png/revision/latest/top-crop/width/300/height/300?cb=20181126200035");
        Store tryNSave = generateStore("A ver si ahorra", "calle 3 223", 3, storeScheduleRepository, multiple, "https://vignette.wikia.nocookie.net/lossimpson/images/1/1b/Trynsave.png/revision/latest/scale-to-width-down/340?cb=20180708175512&path-prefix=es");
        Store allCreatures = generateStore("All Creatures", "calle 1 300", 3, storeScheduleRepository, almacen, "https://img1.wikia.nocookie.net/__cb20101028182609/lossimpson/es/images/e/e8/250px-All_creatures_great_and_cheap.png");
        Store trufaDorada = generateStore("La trufa dorada", "calle 1 350", 3, storeScheduleRepository, almacen, "https://media.malditosnerds.com/adjuntos/290/migration/__export/1543350848181/sites/claro/malditosnerds/img/2015/09/21/aea433_THE_GILDED_TRUFFLE.jpg_875081608.jpg");
        Store luigi = generateStore("Luigi's", "calle 6 5047", 4, storeScheduleRepository, verduleria, "https://vignette.wikia.nocookie.net/simpsons/images/7/79/Luigi%27s.png/revision/latest/top-crop/width/300/height/300?cb=20150518202729");
        Merchandise fideos = new Merchandise("Fideos", "Marolio", 20.5, 12, MerchandiseCategory.GROCERY, "https://jumboargentina.vteximg.com.br/arquivos/ids/539292-750-750/Fideos-Spaghetti-Marolio-500-Gr-1-44037.jpg?v=636989736779430000");
        Merchandise mayonesa = new Merchandise("Mayonesa", "Helmanns", 15.0, 12, MerchandiseCategory.GROCERY, "https://cdn11.bigcommerce.com/s-3stx4pub31/images/stencil/608x608/products/492/1313/mayonesa950__07850.1563569504.jpg?c=2");
        Merchandise mostaza = new Merchandise("Mostaza", "Savora", 21.5, 12, MerchandiseCategory.GROCERY, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2440460_f.jpg");
        Merchandise arroz = new Merchandise("Arroz", "Gallo", 25.0, 12, MerchandiseCategory.GROCERY, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2290212_f.jpg");
        Merchandise lavandina = new Merchandise("Lavandina", "Ayudin", 50.45, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://statics.dinoonline.com.ar/imagenes/large_460x460/2830186_l.jpg");
        Merchandise trapo = new Merchandise("Trapo de piso", "Me lo llevo del dpto de la costa", 25.0, 12, MerchandiseCategory.CLEANING_SUPPLIES, "http://www.aromenai.com/wp-content/uploads/2017/09/trapo-piso-duramas-gris-630x630.jpg");
        Merchandise helado = new Merchandise("Chomp", "Frigor", 150.0, 12, MerchandiseCategory.FROZEN_FOOD, "https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/6/7613034790194_02.jpg");
        Merchandise ferne = new Merchandise("Fernet", "Branca", 400.0, 12, MerchandiseCategory.ALCOHOLIC_DRIKS, "https://d26lpennugtm8s.cloudfront.net/stores/447/761/products/fernet-branca-1l-011-2ae9281c7fca470b9515882681698500-1024-1024.jpg");
        Merchandise papitasN = new Merchandise("Papas Noisette", "McCain", 180.0, 12, MerchandiseCategory.FROZEN_FOOD, "http://www.mccain.com.ar/wp-content/uploads/2013/12/xInternaProd_Noisettes2.png.pagespeed.ic.GssU4n5J64.webp");
        Merchandise comidaPerro = new Merchandise("Alimento Balanceado", "Excelent", 500.0, 12, MerchandiseCategory.PET_STUFFS, "https://puppis.vteximg.com.br/arquivos/ids/167600-600-600/150040.jpg?v=637021773071770000");
        Merchandise nesquik = new Merchandise("Cereal", "Nesquik", 180.0, 12, MerchandiseCategory.CEREALS, "https://cdn.shopify.com/s/files/1/1380/8833/products/Nesquik_400g_Cereales_540x.png?v=1589459970");
        Merchandise sugus = new Merchandise("Caramelos", "Sugus", 150.0, 12, MerchandiseCategory.SWEET_AND_CANDY, "https://www.distribuidorapop.com.ar/wp-content/uploads/2016/06/caramelos-sugus-venta.jpg.webp");
        Merchandise pañales = new Merchandise("Pañales", "Pampers", 600.0, 12, MerchandiseCategory.BABY_CARE, "https://www.toymania.com.ar/wp-content/uploads/2019/03/921221-MLA29624880921_032019-O.jpg");
        Merchandise donuts = new Merchandise("Donas", "The Simpons", 200.0, 12, MerchandiseCategory.BAKERY, "https://cdn.metro-group.com/de/de_pim_210693002001_01.png?w=400&h=400&mode=pad");
        Merchandise pan = new Merchandise("Pan Lactal", "Bimbo", 120.0, 12, MerchandiseCategory.BAKERY, "https://tienda.tvmutual.com.ar/262267-thickbox_default/pan-liviano-xl-730g-bimbo.jpg");
        Merchandise mantequilla = new Merchandise("Mantequilla de Mani", "King", 235.0, 12, MerchandiseCategory.GROCERY, "https://saboresandinos.com/wp-content/uploads/2020/05/DSC_0026-scaled.jpg");
        Merchandise banana = new Merchandise("Banana", "Ecuador", 100.0, 12, MerchandiseCategory.GREENGROCER, "https://www.bidfoodiberia.com/media/catalog/product/cache/1/image/600x/17f82f742ffe127f42dca9de82fb58b1/p/l/ple2.jpg");
        Merchandise manzana = new Merchandise("Manzana", "Moño Azul", 95.0, 12, MerchandiseCategory.GREENGROCER, "https://statics.dinoonline.com.ar/imagenes/large_460x460/3390810_l.jpg");
        Merchandise cerveza = new Merchandise("Cerveza Roja", "Andes", 150.0, 12, MerchandiseCategory.ALCOHOLIC_DRIKS, "https://statics.dinoonline.com.ar/imagenes/large_460x460/3100628_l.jpg");
        Merchandise mermelada = new Merchandise("Mermelada de Frutos Rojos", "BC", 12.0, 12, MerchandiseCategory.GROCERY, "https://walmartar.vteximg.com.br/arquivos/ids/847191-292-292/0779336082630-1.jpg?v=636942158415200000");
        Merchandise chocolate = new Merchandise("Chocolate con Mani", "Shot", 80.0, 12, MerchandiseCategory.SWEET_AND_CANDY, "https://http2.mlstatic.com/chocolate-shot-170-grs-tableta-barata-la-golosineria-D_NQ_NP_644956-MLA41456237493_042020-O.webp");
        Merchandise mym = new Merchandise("Confites de chocolate", "M&M", 100.0, 12, MerchandiseCategory.SWEET_AND_CANDY, "https://elpoderdelconsumidor.org/wp-content/uploads/2017/09/mm.jpg");
        Merchandise chocoPasas = new Merchandise("Chocolate con Pasas", "Cadbury", 130.0, 12, MerchandiseCategory.SWEET_AND_CANDY, "https://jumboargentina.vteximg.com.br/arquivos/ids/182267-750-750/Chocolate-Cadbury-Con-Leche-X-170-Gr-Chocolate-Cadbury-Con-Leche-Con-Pasas-De-Uva-Tableta-170-G-1-17725.jpg?v=636383427679270000");
        Merchandise milaSoja = new Merchandise("Milanesas de Soja", "Granja del Sol", 85.0, 12, MerchandiseCategory.FROZEN_FOOD, "https://walmartar.vteximg.com.br/arquivos/ids/802951-292-292/Milanesa-De-Soja-Granja-Del-Sol-330-Gr-4-U-1-35619.jpg?v=636252834325800000");
        Merchandise coca = new Merchandise("Gaseosa Cola Original 1.5 Lts", "Coca-Cola", 75.0, 12, MerchandiseCategory.DRINKS, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/3080013_f.jpg");
        Merchandise cocaZero = new Merchandise("Gaseosa Cola Sin Azucar 1.5 Lts", "Coca-Cola", 75.0, 12, MerchandiseCategory.DRINKS, "https://www.casa-segal.com/wp-content/uploads/2020/03/coca-cola-sin-azucares-15L-almacen-gaseosas-casa-segal-mendoza.jpg");
        Merchandise cocaLata = new Merchandise("Gaseosa Cola Original Lata", "Coca-Cola", 50.0, 12, MerchandiseCategory.DRINKS, "https://static.elcontainer.cl/2094-big_default/pack-24-o-48-latas-de-coca-cola-regular-zero-o-light.jpg");
        Merchandise cocaDosLts = new Merchandise("Gaseosa Cola Original 2.25 Lts", "Coca-Cola", 130.0, 12, MerchandiseCategory.DRINKS, "https://www.dumbomania.com/wp-content/uploads/2019/04/Coca-Cola-2.25-Litros.jpg");
        Merchandise cocalataZero = new Merchandise("Gaseosa Cola Sin Azucar Lata", "Coca-Cola", 50.0, 12, MerchandiseCategory.DRINKS, "https://static3.elcontainer.cl/2095-big_default/pack-24-o-48-latas-de-coca-cola-regular-zero-o-light.jpg");
        Merchandise rexona = new Merchandise("Desodorante Corporal Masculino", "Rexona", 75.30, 12, MerchandiseCategory.PERSONAL_CARE, "https://www.vassallo.com.ar/8514-large_default/rexona-deo-aer-men-hombre-x90g.jpg");
        Merchandise sobrecitoPollo = new Merchandise("Sobrecito Adulto Sabor Pollo", "Pedigree", 33.0, 12, MerchandiseCategory.PET_STUFFS, "https://puppis.vteximg.com.br/arquivos/ids/163559-600-600/Sobre-AdultoPollo-01_0010.png?v=636739936345170000");
        Merchandise sobrecitoCarne = new Merchandise("Sobrecito Adulto Sabor Carne", "Pedigree", 33.0, 12, MerchandiseCategory.PET_STUFFS,"https://puppis.vteximg.com.br/arquivos/ids/163558-600-600/Sobre-AdultoCarne-01_0010.jpg?v=636739934687870000");
        Merchandise sobrecitoCachPollo = new Merchandise("Sobrecito Cachorro Sabor Pollo", "Pedigree", 33.0, 12, MerchandiseCategory.PET_STUFFS, "https://puppis.vteximg.com.br/arquivos/ids/163560-600-600/Sobre-Cachorro-01_0010.png?v=636739938081070000");
        Merchandise sobrecitoCachCarne = new Merchandise("Sobrecito Cachorro Sabor Carne", "Pedigree", 33.0, 12, MerchandiseCategory.PET_STUFFS, "https://puppis.vteximg.com.br/arquivos/ids/163560-600-600/Sobre-Cachorro-01_0010.png?v=636739938081070000");
        Merchandise quaker = new Merchandise("Cereales Honey Graham", "Quaker", 175.0, 12, MerchandiseCategory.CEREALS, "https://jumboargentina.vteximg.com.br/arquivos/ids/549670-1000-1000/Cereales-Quaker-Honey-Graham-200-Gr-1-47107.jpg?v=637051080751930000");
        Merchandise colgate = new Merchandise("Pasta Dental 180gr", "Colgate", 75.0, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2850744_1_f.jpg");
        Merchandise detergente = new Merchandise("Detergente Lavavajilla Limon 300ml", "Cif", 98.25, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://www.cif.com.ar/content/dam/unilever/cif/argentina/pack_shot/7791290012189-998917-png.png");
        Merchandise cepilloDientes = new Merchandise("Cepillo de Dientes", "Oral-B", 119.80, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://res.cloudinary.com/mtree/image/upload/f_auto,q_auto/OralB_LATAM_ES/es/-/media/OralB_LATAM/Images/Products/ProductPackshots/Cepillo_OralB_Ortodoncia_1200.png?w=118&v=1-201804200647");
        Merchandise shampoo = new Merchandise("Shampoo", "Pantene", 196.40, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://www.toymania.com.ar/wp-content/uploads/2019/09/pantenemax-sh-rest-400ml.jpg");
        Merchandise acondicionador = new Merchandise("Acondicionador", "Pantene", 196.40, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://perfumeriaspigmento.com.ar/media/catalog/product/cache/image/620x678/e9c3970ab036de70892d86c6d221abfe/7/9/79728.jpg");
        Merchandise desengrasante = new Merchandise("Antigrasa 450ml", "Cif", 50.0, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://www.newcasmont.com/12561-large_default/cif-antigrasa-450ml-sachet.jpg");
        Merchandise jabonTocador = new Merchandise("Jabon de Tocador", "Dove", 45.0, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://farmaciasdelplata.com/wp-content/uploads/2019/12/7898422746759.jpg");
        Merchandise jabonRopa = new Merchandise("Jabon Liquido para la Ropa 800ml", "Ariel", 141.0, 12, MerchandiseCategory.CLEANING_SUPPLIES, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2841578_f.jpg");

        Merchandise fideosLuchetti = new Merchandise("Fideos Tirabuzón", "Lucchetti", 45.0, 12, MerchandiseCategory.GROCERY, "https://www.pasosonline.com.ar/wp-content/uploads/2018/08/01AAA-tirabuzon.jpg");
        Merchandise polenta = new Merchandise("Polenta 500gr", "Presto Pronta", 92.25, 12, MerchandiseCategory.GROCERY, "https://www.multifood.com.ar/images/000Z-001-016-01755780Z-001-016-017-PrestoPronta-Polenta.jpg");
        Merchandise aceite = new Merchandise("Aceite 900ml", "Natura", 114.25, 12, MerchandiseCategory.GROCERY, "https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2320031_f.jpg");
        Merchandise lexe = new Merchandise("Leche Descremada", "Las Tres Niñas", 62.5, 12, MerchandiseCategory.GROCERY, "https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/image/1000x/040ec09b1e35df139433887a97daa66f/7/7/7798338290035_02.jpg");
        Merchandise rumba = new Merchandise("Galletitas Dulces", "Rumba", 41.0, 12, MerchandiseCategory.GROCERY, "https://don-gaucho.com/wp-content/uploads/2019/01/Rumba.jpg");
        Merchandise oreo = new Merchandise("Galletitas Dulces", "Oreo", 63.0, 12, MerchandiseCategory.GROCERY, "https://elsuperdelagolosina.com.ar/wp-content/uploads/2018/07/GALLETITAS-OREO-117G1-570x570.jpg");
        Merchandise oregano = new Merchandise("Oregano", "Alicante", 52.20, 12, MerchandiseCategory.GROCERY, "https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/image/1000x/040ec09b1e35df139433887a97daa66f/7/7/7790150540350_01.jpg");
        Merchandise cafe = new Merchandise("Cafe Instantaneo", "Dolca", 252.30, 12, MerchandiseCategory.GROCERY, "https://walmartar.vteximg.com.br/arquivos/ids/858334-1000-1000/Cafe-Instantaneo-Dolca-Clasic-Frasco-Nescafe-100-Gr-1-469317.jpg?v=637175918297900000");



        storeService.addStore(historietas);
        storeService.addMerchandiseToStore(historietas.id(), fideos);
        storeService.addMerchandiseToStore(historietas.id(), mostaza);
        storeService.addStore(kwickEMart);
        storeService.addMerchandiseToStore(kwickEMart.id(), mayonesa);
        storeService.addMerchandiseToStore(kwickEMart.id(),arroz);
        storeService.addMerchandiseToStore(kwickEMart.id(),pan);
        storeService.addMerchandiseToStore(kwickEMart.id(), fideosLuchetti);
        storeService.addMerchandiseToStore(kwickEMart.id(),polenta);
        storeService.addMerchandiseToStore(kwickEMart.id(),aceite);
        storeService.addMerchandiseToStore(kwickEMart.id(), lexe);
        storeService.addMerchandiseToStore(kwickEMart.id(),rumba);
        storeService.addMerchandiseToStore(kwickEMart.id(),oreo);
        storeService.addMerchandiseToStore(kwickEMart.id(), oregano);
        storeService.addMerchandiseToStore(kwickEMart.id(),cafe);
        storeService.addStore(leftorium);
        storeService.addStore(cents);
        storeService.addMerchandiseToStore(cents.id(), manzana);
        storeService.addMerchandiseToStore(cents.id(), banana);
        storeService.addStore(helados);
        storeService.addMerchandiseToStore(helados.id(), helado);
        storeService.addStore(donas);
        storeService.addMerchandiseToStore(donas.id(), donuts);
        storeService.addStore(edna);
        storeService.addMerchandiseToStore(edna.id(), sugus);
        storeService.addMerchandiseToStore(edna.id(), chocolate);
        storeService.addMerchandiseToStore(edna.id(), mym );
        storeService.addMerchandiseToStore(edna.id(), chocoPasas);
        storeService.addStore(gorras);
        storeService.addStore(moe);
        storeService.addMerchandiseToStore(moe.id(), cerveza);
        storeService.addStore(cleaner);
        storeService.addMerchandiseToStore(cleaner.id(), lavandina);
        storeService.addMerchandiseToStore(cleaner.id(), trapo);
        storeService.addMerchandiseToStore(cleaner.id(), pañales);
        storeService.addMerchandiseToStore(cleaner.id(), rexona);
        storeService.addMerchandiseToStore(cleaner.id(), colgate);
        storeService.addMerchandiseToStore(cleaner.id(), detergente);
        storeService.addMerchandiseToStore(cleaner.id(), cepilloDientes);
        storeService.addMerchandiseToStore(cleaner.id(), shampoo);
        storeService.addMerchandiseToStore(cleaner.id(), acondicionador);
        storeService.addMerchandiseToStore(cleaner.id(), desengrasante);
        storeService.addMerchandiseToStore(cleaner.id(), jabonTocador);
        storeService.addMerchandiseToStore(cleaner.id(), jabonRopa);
        storeService.addStore(burger);
        storeService.addStore(monstruomercado);
        storeService.addMerchandiseToStore(monstruomercado.id(), ferne);
        storeService.addMerchandiseToStore(monstruomercado.id(), mantequilla);
        storeService.addMerchandiseToStore(monstruomercado.id(), mermelada);
        storeService.addMerchandiseToStore(monstruomercado.id(), quaker);
        storeService.addMerchandiseToStore(monstruomercado.id(), nesquik);
        storeService.addStore(tryNSave);
        storeService.addMerchandiseToStore(tryNSave.id(), milaSoja);
        storeService.addMerchandiseToStore(tryNSave.id(), coca);
        storeService.addMerchandiseToStore(tryNSave.id(), cocaDosLts);
        storeService.addMerchandiseToStore(tryNSave.id(), cocaZero);
        storeService.addMerchandiseToStore(tryNSave.id(), cocalataZero);
        storeService.addMerchandiseToStore(tryNSave.id(), cocaLata);
        storeService.addStore(allCreatures);
        storeService.addMerchandiseToStore(allCreatures.id(), comidaPerro);
        storeService.addMerchandiseToStore(allCreatures.id(), sobrecitoPollo);
        storeService.addMerchandiseToStore(allCreatures.id(), sobrecitoCarne);
        storeService.addMerchandiseToStore(allCreatures.id(), sobrecitoCachPollo);
        storeService.addMerchandiseToStore(allCreatures.id(), sobrecitoCachCarne);
        storeService.addStore(trufaDorada);
        storeService.addMerchandiseToStore(trufaDorada.id(), papitasN);
        storeService.addStore(luigi);
    }

    private void generateUsers() {
        userService.addUser("margesimpson@gmail.com", "123456", "Av. Siempreviva 742");
        userService.addUser("homerosimpson@gmail.com", "cerveza", "Av. Siempreviva 742");
        userService.addUser("bartsimpson@gmail.com", "aycaramba", "Av. Siempreviva 742");
        userService.addUser("lisasimpson@gmail.com", "yolosetodo", "Av. Siempreviva 742");
        userService.addUser("maggiesimpson@gmail.com", "nohabla", "Av. Siempreviva 742");
    }

    private Store generateStore(String name, String address, Integer distanceInKm, StoreScheduleRepository storeScheduleRepository, List<StoreCategory> categories, String imageUrl) {
        List<String> paymentMethods = Arrays.asList("Efectivo", "Tarjeta de credito");
        List<DayOfWeek> days = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY);
        StoreSchedule schedule = new StoreSchedule(days, LocalTime.of(9,0), LocalTime.of(17, 0));
        storeScheduleRepository.save(schedule);
        return new Store(name, categories, address, distanceInKm, paymentMethods,schedule, LocalDate.now(), imageUrl);
    }

}
