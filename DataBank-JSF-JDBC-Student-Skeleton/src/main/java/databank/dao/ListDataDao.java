/********************************************************************************************************2*4*w*
 * File:  ListDataDao.java Course materials CST8277
 *
  * @author Linh Vo
 * Student number: 041049754
 * Email: vo000077@algonquinlive.com
 */
package databank.dao;

import java.util.List;

/**
 * Description:  API for reading list data from the database
 */
public interface ListDataDao {

	public List<String> readAllPrograms();

}