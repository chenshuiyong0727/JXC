package com.ledao.controller.admin;

import com.ledao.entity.Goods;
import com.ledao.entity.Log;
import com.ledao.model.ResponseMsg;
import com.ledao.service.CustomerReturnListGoodsService;
import com.ledao.service.GoodsService;
import com.ledao.service.LogService;
import com.ledao.service.SaleListGoodsService;
import com.ledao.util.FileUtils;
import com.ledao.util.HttpClientUtil;
import com.ledao.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 后台管理商品Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 13:54
 */
@RestController
@RequestMapping("/admin/goods")
public class GoodsAdminController {

  @Resource
  private GoodsService goodsService;

  @Resource
  private SaleListGoodsService saleListGoodsService;

  @Resource
  private CustomerReturnListGoodsService customerReturnListGoodsService;

  @Resource
  private LogService logService;

  @Value("${file-save-path}")
  private String fileSavePath;

  /**
   * 分页查询商品信息
   */
  @RequestMapping("/list")
  @RequiresPermissions(value = {"商品管理", "进货入库", "退货出库"}, logical = Logical.OR)
  public Map<String, Object> list(Goods searchGoods,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "rows", required = false) Integer rows) {
    Map<String, Object> resultMap = new HashMap<>(16);
    List<Goods> goodsList = goodsService.list(searchGoods, page, rows);
    Long total = goodsService.getCount(searchGoods);
    resultMap.put("rows", goodsList);
    resultMap.put("total", total);
    logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
    return resultMap;
  }

  /**
   * 查询库存报警商品
   */
  @RequestMapping("/listAlarm")
  @RequiresPermissions(value = "库存报警")
  public Map<String, Object> listAlarm() {
    Map<String, Object> resultMap = new HashMap<>(16);
    resultMap.put("rows", goodsService.listAlarm());
    logService.save(new Log(Log.SEARCH_ACTION, "查询库存报警商品信息"));
    return resultMap;
  }

  /**
   * 根据条件分页查询商品库存信息
   */
  @RequestMapping("/listInventory")
  @RequiresPermissions(value = "当前库存查询")
  public Map<String, Object> listInventory(Goods searchGoods,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "rows", required = false) Integer rows) {
    Map<String, Object> resultMap = new HashMap<>(16);
    List<Goods> goodsList = goodsService.list(searchGoods, page, rows);
    for (Goods goods : goodsList) {
      //设置销售总量
      goods.setSaleTotal(
          saleListGoodsService.getTotalByGoodsId(goods.getId()) - customerReturnListGoodsService
              .getTotalByGoodsId(goods.getId()));
    }
    Long total = goodsService.getCount(searchGoods);
    resultMap.put("rows", goodsList);
    resultMap.put("total", total);
    logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
    return resultMap;
  }

  /**
   * 根据条件分页查询没有库存的商品信息
   */
  @RequestMapping("/listNoInventoryQuantity")
  @RequiresPermissions(value = "期初库存")
  public Map<String, Object> listNoInventoryQuantity(
      @RequestParam(value = "codeOrName", required = false) String codeOrName,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "rows", required = false) Integer rows) {
    Map<String, Object> resultMap = new HashMap<>(16);
    List<Goods> goodsList = goodsService
        .listNoInventoryQuantityByCodeOrName(codeOrName, page, rows);
    Long total = goodsService.getCountNoInventoryQuantityByCodeOrName(codeOrName);
    resultMap.put("rows", goodsList);
    resultMap.put("total", total);
    logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息(无库存)"));
    return resultMap;
  }

  /**
   * 根据条件分页查询有库存的商品信息
   */
  @RequestMapping("/listHasInventoryQuantity")
  @RequiresPermissions(value = "期初库存")
  public Map<String, Object> listHasInventoryQuantity(
      @RequestParam(value = "codeOrName", required = false) String codeOrName,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "rows", required = false) Integer rows) {
    Map<String, Object> resultMap = new HashMap<>(16);
    List<Goods> goodsList = goodsService
        .listHasInventoryQuantityByCodeOrName(codeOrName, page, rows);
    Long total = goodsService.getCountHasInventoryQuantityByCodeOrName(codeOrName);
    resultMap.put("rows", goodsList);
    resultMap.put("total", total);
    logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息(有库存)"));
    return resultMap;
  }

  /**
   * 生成商品编码
   */
  @RequestMapping("/genGoodsCode")
  @RequiresPermissions(value = "商品管理")
  public String genGoodsCode() {
    String maxGoodsCode = goodsService.getMaxGoodsCode();
    if (StringUtil.isNotEmpty(maxGoodsCode)) {
      Integer code = Integer.parseInt(maxGoodsCode) + 1;
      StringBuilder codes = new StringBuilder(code + "");
      int length = codes.length();
      for (int i = 4; i > length; i--) {
        codes.insert(0, "0");
      }
      return codes.toString();
    } else {
      return "0001";
    }
  }

  /**
   * 添加或者修改商品信息
   */
  @RequestMapping("/save")
  @RequiresPermissions(value = "商品管理")
  public Map<String, Object> save(Goods goods, HttpServletRequest request, HttpServletRequest req)
      throws Exception {
    MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
    MultipartFile mfile = mreq.getFile("file");//advertImgTemp是input file的name的值
    Map<String, Object> resultMap = this.uploadImage(mfile, req);
    if (goods.getId() != null) {
      goods.setInventoryQuantity(goodsService.findById(goods.getId()).getInventoryQuantity());
      logService.save(new Log(Log.UPDATE_ACTION, "更新商品信息" + goods));
    } else {
      logService.save(new Log(Log.ADD_ACTION, "添加商品信息" + goods));
      goods.setInventoryQuantity(0);
      goods.setLastPurchasingPrice(goods.getPurchasingPrice());
    }
    if ("success".equals(resultMap.get("status").toString()) && resultMap.get("url") != null) {
      goods.setImgUrl(resultMap.get("url").toString());
    }
    if (StringUtil.isNotEmpty(goods.getName())) {
      goods.setName(goods.getModel());
    }
    goodsService.save(goods);
    resultMap.put("success", true);
    return resultMap;
  }

  private Map<String, Object> uploadImage(MultipartFile file, HttpServletRequest req) {
    Map<String, Object> result = new HashMap<>();
    //获取文件的名字
    String originName = file.getOriginalFilename();
    System.out.println("originName:" + originName);
    if (StringUtil.isEmpty(originName)) {
      result.put("status", "error");
      result.put("msg", "图片为空");
      return result;
    }
    //判断文件类型
//    if (!originName.endsWith(".jpg")) {
//      result.put("status", "error");
//      result.put("msg", "文件类型不对");
//      return result;
//    }
    //给上传的文件新建目录
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
    String format = sdf.format(new Date());
    String realPath = fileSavePath + format;
    System.out.println("realPath:" + realPath);
    //若目录不存在则创建目录
    File folder = new File(realPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    //给上传文件取新的名字，避免重名
    String newName = UUID.randomUUID().toString() + ".jpg";
    try {
      //生成文件，folder为文件目录，newName为文件名
      file.transferTo(new File(folder, newName));
      //生成返回给前端的url
      String url =
          req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/images"
              + format + newName;
      System.out.println("url:" + url);
      //返回URL
      result.put("status", "success");
      result.put("url", url);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      result.put("status", "error");
      result.put("msg", e.getMessage());
    }
    return result;
  }

  /**
   * 删除商品信息
   */
  @RequestMapping("/delete")
  @RequiresPermissions(value = "商品管理")
  public Map<String, Object> delete(Integer id) {
    Map<String, Object> resultMap = new HashMap<>(16);
    Goods goods = goodsService.findById(id);
    if (goods.getState() == 1) {
      resultMap.put("success", false);
      resultMap.put("errorInfo", "该商品已经期初入库，不能删除");
    } else if (goods.getState() == 2) {
      resultMap.put("success", false);
      resultMap.put("errorInfo", "该商品已经发生单据，不能删除");
    } else {
      logService.save(new Log(Log.DELETE_ACTION, "删除商品信息" + goods));
      goodsService.delete(id);
      resultMap.put("success", true);
    }
    return resultMap;
  }

  /**
   * 添加商品到仓库 修改库存以及价格信息
   */
  @RequestMapping("/saveStore")
  @RequiresPermissions(value = "期初库存")
  public Map<String, Object> saveStore(Integer id, Integer num, Float price) {
    Map<String, Object> resultMap = new HashMap<>(16);
    Goods goods = goodsService.findById(id);
    goods.setInventoryQuantity(num);
    goods.setPurchasingPrice(price);
    goods.setLastPurchasingPrice(price);
    goodsService.save(goods);
    logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods + "，价格=" + price + ",库存=" + num));
    resultMap.put("success", true);
    return resultMap;
  }

  @RequestMapping("/deleteStock")
  @RequiresPermissions(value = "期初库存")
  public Map<String, Object> deleteStock(Integer id) {
    Map<String, Object> resultMap = new HashMap<>(16);
    Goods goods = goodsService.findById(id);
    //商品状态:2代表有进货或者销售单据
    int goodsState = 2;
    if (goods.getState() == goodsState) {
      resultMap.put("success", false);
      resultMap.put("errorInfo", "该商品已经发生单据，不能删除!");
    } else {
      goods.setInventoryQuantity(0);
      goodsService.save(goods);
      logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods));
      resultMap.put("success", true);
    }
    return resultMap;
  }

  @RequestMapping("/uploadImage")
  public Map<String, Object> uploadImage(MultipartFile file) {
    Map<String, Object> resultMap = new HashMap<>(16);
    String path = "G:\\dw\\img";
    try {
      String Imagepath = fileSavePath + FileUtils.uploadImage(fileSavePath, file);
      System.out.println(Imagepath);
      return resultMap;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return resultMap;
  }

  @RequestMapping("/getImgUrl")
  public ResponseMsg getImgUrl(String model) {
    Map<String, Object> resultMap = new HashMap<>(16);
    String url = HttpClientUtil.getData(model);
    resultMap.put("success", true);
    resultMap.put("data", url);
    return new ResponseMsg(url);
  }

  @ResponseBody
  @RequestMapping("/doAdd")
  public Object doAdd(HttpServletRequest request, HttpSession session) {
    Map<String, Object> resultMap = new HashMap<>(16);
    try {
      MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
      MultipartFile mfile = mreq.getFile("advertImgTemp");//advertImgTemp是input file的name的值
      String name = mfile.getOriginalFilename();//上传文件的名称
      String extname = name.substring(name.lastIndexOf(".")); //取得.jpg
      String imgPath = UUID.randomUUID().toString() + extname; //重新命名图片名字
      ServletContext servletContext = session.getServletContext();
//realpath D:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\crowdfunding-main\advert
      String realpath = servletContext.getRealPath("/advert");
//path D:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps\crowdfunding-main\advert\img\2cafef49-3827-4afe-9ff6-b89b0f931575.jpg
      String path = realpath + "\\img\\" + imgPath;
      mfile.transferTo(new File(path)); //文件上传.
      return resultMap;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return resultMap;
  }
}
