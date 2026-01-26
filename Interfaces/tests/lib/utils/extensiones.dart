extension StringExtension on String? {
  // lo puso asin el ticher
  bool get isNullOrEmpty => this == null || this!.isEmpty;
}