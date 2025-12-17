# -*- coding: utf-8 -*-
# from odoo import http


# class Adrian(http.Controller):
#     @http.route('/adrian/adrian', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/adrian/adrian/objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('adrian.listing', {
#             'root': '/adrian/adrian',
#             'objects': http.request.env['adrian.adrian'].search([]),
#         })

#     @http.route('/adrian/adrian/objects/<model("adrian.adrian"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('adrian.object', {
#             'object': obj
#         })
