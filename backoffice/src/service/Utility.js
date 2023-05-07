export function formatDate(date) {
    if (date === undefined) return "";
    let dateValidation = date;
    console.log(dateValidation)
    dateValidation = dateValidation.split("T")
    dateValidation = dateValidation[0] + ' ' + dateValidation[1];
    return dateValidation.slice(0, 19)
}

export function formatDate2(date) {
    let jsDate=new Date(date)
    return jsDate.toLocaleDateString("en-US")
}

export const varToString = varObj => Object.keys(varObj)[0]

export const redirectNotConnected = () => {
  if(sessionStorage.getItem("user")===undefined || sessionStorage.getItem("user")==="" || sessionStorage.getItem("user")===null){
      window.location.href=""
  }
}

export const getAuthorId = () => {
  return sessionStorage.getItem("author")
}
