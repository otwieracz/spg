(ns spg.download)

;; https://forums.x-plane.org/index.php?/login/

;; curl 'https://forums.x-plane.org/index.php?/login/' -H 'cookie: __cfduid=d3688128529de8dc0d82d57e5c0b0c4351519371804; ips4_IPSSessionFront=b6leetm7ob6tb3bb30il3fh21a; ips4_ipsTimezone=Europe/Warsaw; ips4_hasJS=true' -H 'origin: https://forums.x-plane.org' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: en-GB,en;q=0.9,en-US;q=0.8,fr;q=0.7' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3346.8 Safari/537.36' -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundarysDWTVO7Vu1wTCwaW' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'cache-control: max-age=0' -H 'authority: forums.x-plane.org' -H 'referer: https://forums.x-plane.org/index.php?/login/' --data-binary $'------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="login__standard_submitted"\r\n\r\n1\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="csrfKey"\r\n\r\n7689d58b3d40473e8d7b8b99f9519597\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="ref"\r\n\r\naHR0cHM6Ly9mb3J1bXMueC1wbGFuZS5vcmcvaW5kZXgucGhwPy9maWxlcy9maWxlLzQyOTExLXN0cmF1YmluZy13YWxsbSVDMyVCQ2hsZS1lZG1zLw==\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="MAX_FILE_SIZE"\r\n\r\n536870912\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="plupload"\r\n\r\ne0824764fdc083bb0ec62977d6daf2c2\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="auth"\r\n\r\ngonet9\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="password"\r\n\r\n70f0c23d3117543\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="remember_me"\r\n\r\n0\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="remember_me_checkbox"\r\n\r\n1\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="signin_anonymous"\r\n\r\n0\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW\r\nContent-Disposition: form-data; name="signin_anonymous_checkbox"\r\n\r\n1\r\n------WebKitFormBoundarysDWTVO7Vu1wTCwaW--\r\n' --compressed

;; https://github.com/dakrone/clj-http#cookiesEBq0za34
